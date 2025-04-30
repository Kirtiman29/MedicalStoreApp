package com.example.medicalstoreapp.data_layer

import com.example.medicalstoreapp.data_layer.response.GetProductResponse
import com.example.medicalstoreapp.data_layer.response.OrderDetailResponce
import com.example.medicalstoreapp.data_layer.response.OrderResponce
import com.example.medicalstoreapp.data_layer.response.getAllUserResponse
import com.example.medicalstoreapp.data_layer.response.getSpecificProductResponce
import com.example.medicalstoreapp.data_layer.response.getSpecificUserResponce
import com.example.medicalstoreapp.data_layer.response.loginResponse
import com.example.medicalstoreapp.data_layer.response.signUpResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface apiServices {


    @GET("get_order_detail")
    suspend fun getOrderDetail(): Response<OrderDetailResponce>

    @FormUrlEncoded
    @POST("getSpecificProduct")
    suspend fun getSpecificProduct(
        @Field("product_id") product_id: String,
    ): Response<getSpecificProductResponce>

    @FormUrlEncoded
    @POST("getSpecificUser_data")
    suspend fun getSpecificUser(
        @Field("user_id") user_id: String,
    ): Response<getSpecificUserResponce>


    @FormUrlEncoded
    @POST("createUser")
    suspend fun signUpUser(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("pincode") pincode: String,
        @Field("phone_number") phone_number: String,
        @Field("address") address: String,
    ): Response<signUpResponse>

    @FormUrlEncoded
    @POST("user_addStock")
    suspend fun UserOrder(
        @Field("user_id") user_id: String,
        @Field("product_id") product_id: String,
        @Field("product_name") product_name: String,
        @Field("quantity") quantity: Int,
        @Field("total_amount") total_amount: Float,
        @Field("price") price: String,
        @Field("category") category: String,
        @Field("user_name") user_name: String,
        @Field("message") message: String,
    ): Response<OrderResponce>


    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<loginResponse>

    @GET("getAllUser")
    suspend fun getAllUser(): Response<getAllUserResponse>

    @GET("get_all_product")
    suspend fun getAllProduct(): Response<GetProductResponse>

}