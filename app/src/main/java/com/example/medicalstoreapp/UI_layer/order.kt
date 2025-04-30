package com.example.medicalstoreapp.UI_layer

data class Order(
    val order_id: String,
    val user_id: String,
    val user_name: String,
    val product_name: String,
    val category: String,
    val price: String,
    val quantity: Int,
    val total_amount: String,
    val isApproved: Int
)
