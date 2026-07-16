package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.data.db.CartItem
import com.example.domain.models.Product
import com.example.ui.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
    onProductClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    val allProducts = listOf(
        Product("1", "Paracetamol 500mg", "Medicine", 500.0, "₦500"),
        Product("2", "Digital Thermometer", "Supplies", 2500.0, "₦2,500"),
        Product("3", "Vitamin C 1000mg", "Wellness", 1200.0, "₦1,200"),
        Product("4", "Ibuprofen 400mg", "Medicine", 800.0, "₦800"),
        Product("5", "First Aid Kit", "Supplies", 5500.0, "₦5,500"),
        Product("6", "Cough Syrup", "Medicine", 1500.0, "₦1,500"),
        Product("7", "Face Masks (50 pcs)", "Supplies", 3000.0, "₦3,000"),
        Product("8", "Multivitamins", "Wellness", 4500.0, "₦4,500")
    )
    
    val filteredProducts = if (searchQuery.isBlank()) {
        allProducts
    } else {
        allProducts.filter { 
            it.name.contains(searchQuery, ignoreCase = true) || 
            it.category.contains(searchQuery, ignoreCase = true) 
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Search Products",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search medicines, supplies, wellness...") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (filteredProducts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No products found for \"$searchQuery\"",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = { onProductClick(product.id) },
                        onAddToCart = {
                            cartViewModel.addToCart(
                                CartItem(
                                    productId = product.id,
                                    name = product.name,
                                    category = product.category,
                                    price = product.price,
                                    quantity = 1
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
