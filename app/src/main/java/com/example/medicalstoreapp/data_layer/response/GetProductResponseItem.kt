package com.example.medicalstoreapp.data_layer.response


data class GetProductResponseItem(
    val category: String,
    val expire_date: String,
    val id: Int,
    val price: Double,
    val product_id: String,
    val product_name: String,
    val stock: Int
)