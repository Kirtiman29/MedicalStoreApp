package com.example.medicalstoreapp.data_layer

import com.example.medicalstoreapp.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object apiProvider {
    fun provideApi() = Retrofit.Builder().baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(apiServices::class.java)

}