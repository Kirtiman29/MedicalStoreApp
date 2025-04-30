package com.example.medicalstoreapp

 sealed class State <out T> {
     object Loading : State<Nothing>()
     data class Success<out T>(val data: Any) : State<T>()
     data class Error(val message: String) : State<Nothing>()

}