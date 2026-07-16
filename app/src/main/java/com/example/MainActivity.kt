package com.example

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AiAssistantScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.ContactUsScreen
import com.example.ui.screens.ForgotPasswordScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.PrescriptionUploadScreen
import com.example.ui.screens.ProductDetailsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AiViewModel
import com.example.ui.viewmodel.CartViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val cartViewModel: CartViewModel = viewModel(
          factory = androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
        val aiViewModel: AiViewModel = viewModel()
        MainScreen(cartViewModel = cartViewModel, aiViewModel = aiViewModel)
      }
    }
  }
}

@Composable
fun MainScreen(cartViewModel: CartViewModel, aiViewModel: AiViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf("Home", "Search", "Cart", "Profile")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.ShoppingCart, Icons.Filled.Person)
    val routes = listOf("home", "search", "cart", "profile")

    val authRoutes = listOf("login", "register", "forgot_password")
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (currentRoute != "ai_assistant" && currentRoute?.startsWith("product_details") != true && currentRoute != "prescription_upload" && currentRoute != "admin_dashboard" && currentRoute !in authRoutes) {
                FloatingActionButton(
                    onClick = { navController.navigate("ai_assistant") },
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(Icons.Filled.Spa, contentDescription = "AI Assistant")
                }
            }
        },
        bottomBar = {
            if (currentRoute != "ai_assistant" && currentRoute?.startsWith("product_details") != true && currentRoute != "prescription_upload" && currentRoute != "admin_dashboard" && currentRoute !in authRoutes) {
                NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = currentRoute == routes[index] || (currentRoute == null && index == 0)
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != routes[index]) {
                                navController.navigate(routes[index]) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { 
                HomeScreen(
                    cartViewModel = cartViewModel,
                    onProductClick = { productId -> navController.navigate("product_details/$productId") },
                    onUploadPrescriptionClick = { navController.navigate("prescription_upload") },
                    onBookConsultationClick = { navController.navigate("doctors") }
                ) 
            }
            composable("search") { 
                SearchScreen(
                    cartViewModel = cartViewModel,
                    onProductClick = { productId -> navController.navigate("product_details/$productId") }
                ) 
            }
            composable("cart") { CartScreen(cartViewModel = cartViewModel) }
            composable("profile") { 
                ProfileScreen(
                    onLogout = { navController.navigate("login") { popUpTo(0) } },
                    onAdminClick = { navController.navigate("admin_dashboard") },
                    onContactUsClick = { navController.navigate("contact_us") }
                ) 
            }
            composable("login") {
                LoginScreen(
                    onNavigateToRegister = { navController.navigate("register") },
                    onNavigateToForgotPassword = { navController.navigate("forgot_password") },
                    onLoginSuccess = { navController.navigate("home") { popUpTo(0) } },
                    onAdminLoginSuccess = { navController.navigate("admin_dashboard") { popUpTo(0) } }
                )
            }
            composable("register") {
                RegisterScreen(
                    onNavigateToLogin = { navController.navigate("login") },
                    onRegisterSuccess = { navController.navigate("home") { popUpTo(0) } }
                )
            }
            composable("forgot_password") {
                ForgotPasswordScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable("ai_assistant") { 
                AiAssistantScreen(
                    viewModel = aiViewModel,
                    onBack = { navController.popBackStack() }
                ) 
            }
            composable("product_details/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ProductDetailsScreen(
                    productId = productId,
                    cartViewModel = cartViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("prescription_upload") {
                PrescriptionUploadScreen(onBack = { navController.popBackStack() })
            }
            composable("admin_dashboard") {
                AdminDashboardScreen(onBack = { navController.popBackStack() })
            }
            composable("contact_us") {
                ContactUsScreen(onBack = { navController.popBackStack() })
            }
            composable("doctors") {
                com.example.ui.screens.DoctorsScreen(
                    onBack = { navController.popBackStack() },
                    onBookConsultation = { doctorId -> /* Handle booking */ }
                )
            }
        }
    }
}
