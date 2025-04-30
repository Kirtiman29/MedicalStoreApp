package com.example.medicalstoreapp.navigation

sealed class Screen2(val route: String) {
    object getUserID : Screen2("getUserID/{userId}") {
        fun createRoute(userId: String) = "getUserID/$userId"
    }
}