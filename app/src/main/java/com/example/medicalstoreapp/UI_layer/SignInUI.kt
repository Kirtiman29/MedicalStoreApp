package com.example.medicalstoreapp.UI_layer


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.R
import com.example.medicalstoreapp.common.MultiColorText
import com.example.medicalstoreapp.data_layer.AppViewModel
import com.example.medicalstoreapp.navigation.Home
import com.example.medicalstoreapp.navigation.SignUp
import com.example.medicalstoreapp.navigation.Temporary

@Composable
fun SignInUI(navController: NavHostController, viewModel: AppViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val state = viewModel.loginUserState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    when {
        state.value.loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.value.error.toString())
            }
        }

        state.value.data != null -> {
            Toast.makeText(context, "${state.value.data?.body()?.message}", Toast.LENGTH_SHORT)
                .show()


        }

    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )


            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = email,
                onValueChange = {
                    email = it
                },
                placeholder = {
                    Text(text = "Enter your  email")
                })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = password,
                onValueChange = {
                    password = it
                },
                placeholder = {
                    Text(text = "Enter your password ")
                })

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {
                viewModel.login(
                    email = email,
                    password = password
                )



            }) {
                Text(text = " Login ")

            }
            Spacer(modifier = Modifier.height(10.dp))
            MultiColorText(
                firstText = "Don't have a Account?",
                secondText = "SignUp",
                modifier = Modifier.clickable {
                    navController.navigate(SignUp) {
                        popUpTo(SignUp) {
                            inclusive = true
                        }
                    }

                })
        }
    }

}