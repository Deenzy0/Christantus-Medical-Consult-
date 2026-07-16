package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.db.CartItem
import com.example.ui.viewmodel.CartViewModel
import com.example.domain.models.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, 
    cartViewModel: CartViewModel,
    onProductClick: (String) -> Unit,
    onUploadPrescriptionClick: () -> Unit,
    onBookConsultationClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            HeaderSection()
        }
        item {
            SearchBarSection()
        }
        item {
            HeroBannerSection(onBookConsultationClick)
        }
        item {
            CategoriesSection()
        }
        item {
            UploadPrescriptionBanner(onUploadPrescriptionClick)
        }
        item {
            FeaturedProductsSection(cartViewModel, onProductClick)
        }
        item {
            SpecialOffersSection()
        }
        item {
            WhyChooseUsSection()
        }
        item {
            MeetOurPharmacistsSection()
        }
        item {
            TestimonialsSection()
        }
        item {
            FooterSection()
        }
    }
}

@Composable
fun HeroBannerSection(onBookConsultationClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Professional Healthcare",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Consultations, genuine medicines, and expert advice.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onBookConsultationClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Book Consultation")
                }
            }
        }
    }
}

@Composable
fun WhyChooseUsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Why Choose Christantus Medical Consult",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Your Trusted Partner in Healthcare",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "At Christantus Medical Consult, we believe quality healthcare should be accessible, affordable, and dependable. Our commitment is to provide genuine pharmaceutical products, professional healthcare support, and excellent customer service, helping individuals and families make informed health decisions.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        WhyChooseUsCard(
            title = "Genuine Medicines",
            description = "We source our medicines and healthcare products from trusted suppliers to help ensure quality, safety, and authenticity.",
            icon = Icons.Filled.MedicalServices
        )
        WhyChooseUsCard(
            title = "Professional Healthcare Guidance",
            description = "Our team is committed to providing reliable information and helping customers understand the proper use of medications and healthcare products. Medical concerns that require diagnosis or treatment are referred to qualified healthcare professionals.",
            icon = Icons.Filled.Person
        )
        WhyChooseUsCard(
            title = "Reliable Delivery",
            description = "We work to process orders efficiently and deliver healthcare products securely, while complying with applicable regulations.",
            icon = Icons.Filled.LocalShipping
        )
        WhyChooseUsCard(
            title = "Secure Shopping",
            description = "Your privacy and security matter. We use secure technologies and industry-standard practices to help protect your personal information and payment details.",
            icon = Icons.Filled.Security
        )
        WhyChooseUsCard(
            title = "Prescription Support",
            description = "Where required by law or professional standards, prescription medicines are dispensed only after appropriate prescription verification by qualified personnel.",
            icon = Icons.Filled.Assignment
        )
        WhyChooseUsCard(
            title = "Customer-Centered Care",
            description = "Every customer is treated with professionalism, respect, and compassion. We strive to make healthcare products and services easy to access and understand.",
            icon = Icons.Filled.Favorite
        )
        WhyChooseUsCard(
            title = "Serving Communities",
            description = "From our location in Calabar, we proudly serve customers across Nigeria and are building our capacity to reach more communities throughout Africa.",
            icon = Icons.Filled.Public
        )
        WhyChooseUsCard(
            title = "Responsive Support",
            description = "Whether you need help placing an order, uploading a prescription, or finding a product, our support team is ready to assist.",
            icon = Icons.Filled.SupportAgent
        )
    }
}

@Composable
fun WhyChooseUsCard(title: String, description: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun UploadPrescriptionBanner(onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Upload Prescription",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Get your medicines delivered quickly",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Icon(
                imageVector = Icons.Filled.MedicalServices,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Welcome back,",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Gerald",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun SearchBarSection() {
    var searchQuery by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search medicines, supplies...") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("search_input"),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        singleLine = true
    )
}

@Composable
fun CategoriesSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(icon = Icons.Filled.Vaccines, title = "Medicines")
            CategoryItem(icon = Icons.Filled.MedicalServices, title = "Supplies")
            CategoryItem(icon = Icons.Filled.MonitorHeart, title = "Consult")
            CategoryItem(icon = Icons.Filled.Spa, title = "Wellness")
        }
    }
}

@Composable
fun CategoryItem(icon: ImageVector, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun FeaturedProductsSection(cartViewModel: CartViewModel, onProductClick: (String) -> Unit) {
    val products = listOf(
        Product("1", "Paracetamol 500mg", "Medicine", 500.0, "₦500"),
        Product("2", "Digital Thermometer", "Supplies", 2500.0, "₦2,500"),
        Product("3", "Vitamin C 1000mg", "Wellness", 1200.0, "₦1,200")
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Featured Products",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = { /* TODO */ }) {
                Text("See All", color = MaterialTheme.colorScheme.primary)
            }
        }
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
        ) {
            items(products) { product ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(product: Product, onProductClick: () -> Unit, onAddToCart: () -> Unit) {
    Card(
        onClick = onProductClick,
        modifier = Modifier
            .width(160.dp)
            .testTag("product_card_${product.name}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MedicalServices,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(48.dp)
                )
            }
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = product.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = product.priceString,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth().testTag("add_to_cart_${product.id}"),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Add to Cart")
            }
        }
    }
}

@Composable
fun SpecialOffersSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Special Offers",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Get 20% off your first order!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "Use code: HEALTH20",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun MeetOurPharmacistsSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Meet Our Pharmacists",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PharmacistCard(name = "Dr. Sarah Johnson", specialty = "Clinical Pharmacist", modifier = Modifier.weight(1f))
            PharmacistCard(name = "Dr. Michael Chen", specialty = "Pharmacotherapy Specialist", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PharmacistCard(name: String, specialty: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = androidx.compose.foundation.shape.CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = specialty,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun TestimonialsSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "What Our Customers Say",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        TestimonialCard(
            name = "Jane Doe",
            comment = "Excellent service! The AI assistant was very helpful, and my prescription was delivered quickly."
        )
    }
}

@Composable
fun TestimonialCard(name: String, comment: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\"$comment\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Legal & Information",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        ExpandableLegalSection(
            title = "About Us",
            content = "Welcome to Christantus Medical Consult\n\nChristantus Medical Consult is a pharmacy and healthcare service provider committed to improving access to quality medicines, healthcare products, and professional support. Our goal is to combine modern technology with trusted pharmaceutical services to deliver a convenient and reliable healthcare experience.\n\nLocated at 146 Goldie St, University of Calabar, Calabar 540281, Cross River, Nigeria, we are dedicated to serving individuals, families, healthcare professionals, clinics, and organizations with integrity and care.\n\nOur Mission\nTo improve lives by providing access to quality pharmaceutical products, dependable healthcare services, and exceptional customer experiences through innovation, professionalism, and integrity.\n\nOur Vision\nTo become one of Nigeria's most trusted and respected pharmacy and healthcare platforms, delivering innovative pharmaceutical services that improve health outcomes across Africa.\n\nOur Core Values\n- Integrity\n- Excellence\n- Compassion\n- Innovation\n- Professionalism\n- Customer Focus"
        )
        ExpandableLegalSection(
            title = "Privacy Policy",
            content = "Effective Date: [Insert Date]\nChristantus Medical Consult respects your privacy and is committed to protecting your personal information.\n\nInformation We Collect\nWe may collect:\n- Name, Email address, Phone number\n- Delivery address, Account information, Order history\n- Prescription uploads, Consultation requests\n- Payment transaction references\n\nHow We Use Your Information\nYour information may be used to process orders, deliver products, verify prescriptions, schedule consultations, respond to customer enquiries, and meet legal obligations.\n\nInformation Security\nWe implement reasonable administrative, technical, and organizational safeguards to help protect your information.\n\nYour Rights\nSubject to applicable laws, you may have the right to access, correct, or delete your personal information."
        )
        ExpandableLegalSection(
            title = "Terms & Conditions",
            content = "By using Christantus Medical Consult, you agree to comply with these terms.\n- You must provide accurate information when creating an account or placing an order.\n- Prescription medicines may require verification before they are dispensed.\n- Product availability and pricing may change without prior notice.\n- We reserve the right to refuse or cancel orders where required by law, safety considerations, or verification requirements.\n- Misuse of the website is prohibited."
        )
        ExpandableLegalSection(
            title = "Shipping Policy",
            content = "We aim to process and dispatch eligible orders as quickly as possible. Delivery times vary depending on destination and product availability. Shipping charges are displayed during checkout. Customers are responsible for providing accurate delivery information."
        )
        ExpandableLegalSection(
            title = "Returns & Refund Policy",
            content = "Customer satisfaction is important to us. Returns may be accepted for eligible products in accordance with applicable laws and our quality standards. Certain products, including many medicines and healthcare items, may not be eligible for return due to safety and regulatory requirements unless they are defective, damaged, or supplied in error."
        )
        ExpandableLegalSection(
            title = "Prescription Policy",
            content = "Prescription-only medicines require a valid prescription issued by an authorized healthcare professional where required by applicable regulations. We reserve the right to verify prescriptions, request additional information, and decline dispensing where legal or professional requirements are not met."
        )
        ExpandableLegalSection(
            title = "Medical Disclaimer",
            content = "The information provided on this website is for general informational purposes only and should not be considered medical advice, diagnosis, or treatment. Always consult a qualified healthcare professional regarding your medical condition, medications, or treatment options. Use of the AI Health Assistant does not establish a healthcare professional–patient relationship and is intended only to provide general informational assistance."
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Christantus Medical Consult\n📍 146 Goldie St, University of Calabar, Calabar 540281, Cross River, Nigeria\n📞 08148445752\n✉️ Christantusmedicalconsult@outlook.com",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ExpandableLegalSection(title: String, content: String) {
    var expanded by remember { mutableStateOf(false) }
    
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
