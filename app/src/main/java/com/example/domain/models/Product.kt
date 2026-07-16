package com.example.domain.models

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val priceString: String,
    val description: String = "This is a premium healthcare product provided by Christantus Medical Consult. It is guaranteed to be 100% genuine and safe for use as prescribed."
)
