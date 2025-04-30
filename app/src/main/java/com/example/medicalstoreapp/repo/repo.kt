package com.example.medicalstoreapp.repo

import com.example.medicalstoreapp.State
import com.example.medicalstoreapp.data_layer.apiProvider
import com.example.medicalstoreapp.data_layer.response.GetProductResponse
import com.example.medicalstoreapp.data_layer.response.OrderDetailResponce
import com.example.medicalstoreapp.data_layer.response.OrderResponce
import com.example.medicalstoreapp.data_layer.response.getAllUserResponse
import com.example.medicalstoreapp.data_layer.response.getSpecificProductResponce
import com.example.medicalstoreapp.data_layer.response.getSpecificUserResponce
import com.example.medicalstoreapp.data_layer.response.loginResponse
import com.example.medicalstoreapp.data_layer.response.signUpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class repo {

    suspend fun getOrderDetailRepo(): Flow<State<Response<OrderDetailResponce>>> = flow {
        emit(State.Loading)
        try {
            val getOrderDetailResponse = apiProvider.provideApi().getOrderDetail()
            emit(State.Success(getOrderDetailResponse))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun getSpecificProductRepo(
        product_id: String,
    ): Flow<State<Response<getSpecificProductResponce>>> = flow {
        emit(State.Loading)
        try{
            val getSpecificProductRes = apiProvider.provideApi()
                .getSpecificProduct(product_id = product_id)
            emit(State.Success(getSpecificProductRes))

        } catch (e: Exception){
            emit(State.Error(e.message.toString()))
        }
    }


    suspend fun getSpecificUserRepo(
        user_id: String
    ) : Flow<State<Response<getSpecificUserResponce>>> = flow{
        emit(State.Loading)
        try{
            val response = apiProvider.provideApi().getSpecificUser(user_id = user_id)
            emit(State.Success(response))
        }catch (e:Exception){
            emit(State.Error(e.message.toString()))
        }
    }


    suspend fun getAllProductRepo(): Flow<State<Response<GetProductResponse>>> = flow {
        emit(State.Loading)

        try {
            val getAllProductResponse = apiProvider.provideApi().getAllProduct()
            emit(State.Success(getAllProductResponse))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun getAllUser(): Flow<State<Response<getAllUserResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = apiProvider.provideApi().getAllUser()
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }


    }

    suspend fun signUpUser(
        name: String,
        password: String,
        email: String,
        pincode: String,
        phoneNumber: String,
        address: String
    ): Flow<State<Response<signUpResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = apiProvider.provideApi().signUpUser(
                name = name,
                password = password,
                email = email,
                pincode = pincode,
                phone_number = phoneNumber,
                address = address
            )
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun orderRepo(
        user_id: String,
        product_id: String,
        product_name: String,
        quantity: Int,
        total_amount: Float,
        price: String,
        category: String,
        user_name: String,
        message: String
    ): Flow<State<Response<OrderResponce>>> = flow {
        emit(State.Loading)
        try {
            val response = apiProvider.provideApi().UserOrder(
                user_id = user_id,
                product_id = product_id,
                product_name = product_name,
                quantity = quantity,
                total_amount = total_amount,
                price = price,
                category = category,
                user_name = user_name,
                message = message
            )
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }

    }


    suspend fun loginRepo(
        email: String,
        password: String

    ) : Flow<State<Response<loginResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = apiProvider.provideApi().loginUser(
                email = email,
                password = password
            )
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }

    }
}
