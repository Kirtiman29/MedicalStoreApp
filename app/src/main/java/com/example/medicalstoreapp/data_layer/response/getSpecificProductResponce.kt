package com.example.medicalstoreapp.data_layer.response

data class getSpecificProductResponce(
    val category: String,
    val expire_date: String,
    val id: Int,
    val price: Int,
    val product_id: String,
    val product_name: String,
    val stock: Int
)