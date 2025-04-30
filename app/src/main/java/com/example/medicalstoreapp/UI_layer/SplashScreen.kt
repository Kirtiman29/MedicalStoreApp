package com.example.medicalstoreapp.UI_layer

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.R
import com.example.medicalstoreapp.data_layer.AppViewModel
import com.example.medicalstoreapp.data_layer.userPrefrence.UserPrefrenceManager
import com.example.medicalstoreapp.navigation.ProductDetails
import com.example.medicalstoreapp.navigation.SignIn
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel: AppViewModel = hiltViewModel() ,
                 navHostController: NavHostController,
                 userPrefrenceManager: UserPrefrenceManager
) {
    val userId by userPrefrenceManager.user_id.collectAsState(initial = null)


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = " App Logo",
            modifier = Modifier.size(300.dp)
        )
    }

    LaunchedEffect(userId) {
delay(3000)
//        if(userId != null){
//            navHostController.navigate(ProductDetails)
//        }
//        else  {
//            navHostController.navigate(SignIn)
//
//        }
    }

}