package com.example.medicalstoreapp.data_layer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.State
import com.example.medicalstoreapp.data_layer.response.GetProductResponse
import com.example.medicalstoreapp.data_layer.response.OrderDetailResponce
import com.example.medicalstoreapp.data_layer.response.OrderResponce
import com.example.medicalstoreapp.data_layer.response.getAllUserResponse
import com.example.medicalstoreapp.data_layer.response.getSpecificProductResponce
import com.example.medicalstoreapp.data_layer.response.getSpecificUserResponce
import com.example.medicalstoreapp.data_layer.response.loginResponse
import com.example.medicalstoreapp.data_layer.response.signUpResponse
import com.example.medicalstoreapp.data_layer.userPrefrence.UserPrefrenceManager

import com.example.medicalstoreapp.repo.repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repo: repo,
    private val userPrefrenceManager: UserPrefrenceManager,
) : ViewModel() {

    private val _signUpUserState = MutableStateFlow(SignUpUserState())
    val signUpUserState = _signUpUserState.asStateFlow()

    private val _loginUserState = MutableStateFlow(LoginUserState())
    val loginUserState = _loginUserState.asStateFlow()

    private val _getAllUserState = MutableStateFlow(GetAllUserState())
    val getAllUserState = _getAllUserState.asStateFlow()

    private val _getAllProductState = MutableStateFlow(GetAllProductState())
    val getAllProductState = _getAllProductState.asStateFlow()

    private val _orderState = MutableStateFlow(OrderState())
    val orderState = _orderState.asStateFlow()

    private val _getSpecificUserState = MutableStateFlow(GetSpecificUserState())
    val getSpecificUserState = _getSpecificUserState.asStateFlow()

    private val _getSpecificProductState = MutableStateFlow(GetSpecificProductState())
    val getSpecificProductState = _getSpecificProductState.asStateFlow()

    private val _orderDetailState = MutableStateFlow(OrderDetailState())
    val orderDetailState = _orderDetailState.asStateFlow()

    fun getOrderDetail(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getOrderDetailRepo()
                .collectLatest{
                    when(it){
                        is State.Loading->{
                            _orderDetailState.value = OrderDetailState(loading = true)
                        }
                        is State.Success->{
                            _orderDetailState.value = OrderDetailState(data = it.data as Response<OrderDetailResponce>)
                        }
                        is State.Error->{
                            _orderDetailState.value = OrderDetailState(error = it.message)
                        }
                    }
                }
        }
    }



    fun getSpecificProduct(
        product_id: String,

    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificProductRepo(product_id = product_id)
                .collect{
                    when(it){
                        is State.Loading->{
                            _getSpecificProductState.value = GetSpecificProductState(loading = true)
                        }
                        is State.Success->{
                            _getSpecificProductState.value = GetSpecificProductState(data = it.data as Response<getSpecificProductResponce>)
                        }
                        is State.Error->{
                            _getSpecificProductState.value = GetSpecificProductState(error = it.message)
                        }


                    }
                }
        }
    }

    fun getSpecificUser(user_id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificUserRepo(user_id=user_id)
                .collectLatest{
                    when(it){
                        is State.Loading->{
                            _getSpecificUserState.value = GetSpecificUserState(loading = true)
                        }
                        is State.Success->{
                            val userId = it.data.toString()
                            userPrefrenceManager.saveUserId(userId)
                            _getSpecificUserState.value = GetSpecificUserState(data = it.data as Response<getSpecificUserResponce>)


                        }
                        is State.Error->{
                            _getSpecificUserState.value = GetSpecificUserState(error = it.message)
                        }
                    }
                }

        }

    }

    fun fetchCurrentUser(){
        viewModelScope.launch (Dispatchers.IO){
            val userId = userPrefrenceManager.user_id.firstOrNull()
            Log.d("fetchCurrentUser", "userID:  $userId")
            if (userId.isNullOrBlank()){
                _getSpecificUserState.value = GetSpecificUserState(error = "User not found")

            } else{
                getSpecificUser(userId)
            }

        }
    }


    fun addOrder(
        user_id: String,
        product_id: String,
        product_name: String,
        quantity: Int,
        total_amount: Float,
        price: String,
        category: String,
        user_name: String,
        message: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.orderRepo(
                user_id = user_id,
                product_id = product_id,
                product_name = product_name,
                quantity = quantity,
                total_amount = total_amount,
                price = price,
                category = category,
                user_name = user_name,
                message = message
            ).collect {
                when (it) {
                    is State.Loading -> {
                        _orderState.value = OrderState(loading = true)
                    }

                    is State.Success -> {
                        _orderState.value = OrderState(data = it.data as Response<OrderResponce>)
                    }

                    is State.Error -> {
                        _orderState.value = OrderState(error = it.message)
                    }
                }


            }

        }
    }

        init {
            getAllProduct()
        }

        fun getAllProduct() {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getAllProductRepo().collect {
                    when (it) {
                        is State.Loading -> {
                            _getAllProductState.value = GetAllProductState(loading = true)
                        }

                        is State.Success -> {
                            _getAllProductState.value =
                                GetAllProductState(data = it.data as Response<GetProductResponse>)
                        }

                        is State.Error -> {
                            _getAllProductState.value = GetAllProductState(error = it.message)
                        }
                    }
                }
            }
        }


        fun getAllUser() {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getAllUser().collect {
                    when (it) {
                        is State.Loading -> {
                            _getAllUserState.value = GetAllUserState(loading = true)
                        }

                        is State.Success -> {
                            _getAllUserState.value =
                                GetAllUserState(data = it.data as Response<getAllUserResponse>)
                        }

                        is State.Error -> {
                            _getAllUserState.value = GetAllUserState(error = it.message)
                        }

                    }
                }

            }

        }


        fun signUp(
            name: String,
            password: String,
            email: String,
            pincode: String,
            phoneNumber: String,
            address: String,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.signUpUser(
                    name = name,
                    password = password,
                    email = email,
                    pincode = pincode,
                    phoneNumber = phoneNumber,
                    address = address
                ).collect {
                    when (it) {
                        is State.Loading -> {
                            _signUpUserState.value = SignUpUserState(loading = true)
                        }

                        is State.Success -> {
                            _signUpUserState.value =
                                SignUpUserState(data = it.data as Response<signUpResponse>)
                        }

                        is State.Error -> {
                            _signUpUserState.value = SignUpUserState(error = it.message)
                        }
                    }
                }
            }
        }


        fun login(
            email: String,
            password: String,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.loginRepo(
                    email = email,
                    password = password
                ).collect {
                    when (it) {
                        is State.Loading -> {
                            _loginUserState.value = LoginUserState(loading = true)
                        }

                        is State.Success -> {

                            val userId = it.data.toString()
                            userPrefrenceManager.saveUserId(userId)
                            _loginUserState.value =
                                LoginUserState(data = it.data as Response<loginResponse>)

                        }

                        is State.Error -> {
                            _loginUserState.value = LoginUserState(error = it.message)
                        }
                    }
                }
            }
        }
    }

    data class SignUpUserState(
        val loading: Boolean = false,
        val error: String? = null,
        val data: Response<signUpResponse>? = null,
    )

    data class LoginUserState(
        val loading: Boolean = false,
        val error: String? = null,
        val data: Response<loginResponse>? = null,
    )


    data class GetAllUserState(
        val loading: Boolean = false,
        val error: String? = null,
        val data: Response<getAllUserResponse>? = null,
    )

    data class GetAllProductState(
        val loading: Boolean = false,
        val error: String? = null,
        val data: Response<GetProductResponse>? = null,
    )

    data class OrderState(
        val loading: Boolean = false,
        val error: String? = null,
        val data: Response<OrderResponce>? = null,
    )

data class GetSpecificUserState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: Response<getSpecificUserResponce>? = null,
)


data class GetSpecificProductState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: Response<getSpecificProductResponce>? = null,
)

data class OrderDetailState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: Response<OrderDetailResponce>? = null,
)

