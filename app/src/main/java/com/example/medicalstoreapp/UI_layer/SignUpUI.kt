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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.R
import com.example.medicalstoreapp.common.MultiColorText
import com.example.medicalstoreapp.data_layer.AppViewModel
import com.example.medicalstoreapp.navigation.Home
import com.example.medicalstoreapp.navigation.SignIn
import com.example.medicalstoreapp.navigation.SignUp
import com.example.medicalstoreapp.navigation.Temporary
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun SignUpUI(navController: NavController,viewModel: AppViewModel = hiltViewModel()) {

    val state= viewModel.signUpUserState.collectAsState()
    val context= LocalContext.current

    when{
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
            Toast.makeText(context, "${state.value.data?.body()?.message}", Toast.LENGTH_SHORT).show()
        }



    }



    var userName by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }
    var userPinCode by remember { mutableStateOf("") }
    var userAdress by remember { mutableStateOf("") }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = userName,
                onValueChange = {
                    userName = it
                },
                placeholder = {
                    Text(text = "Enter your name ")
                })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = userPassword,
                onValueChange = {
                    userPassword = it
                },
                placeholder = {
                    Text(text = "Enter your  password")
                })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = userEmail,
                onValueChange = {
                    userEmail = it
                },
                placeholder = {
                    Text(text = "Enter your email ")
                })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = userPhone,
                onValueChange = {
                    userPhone = it
                },
                placeholder = {
                    Text(text = "Enter your phoneNumber  ")
                })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = userPinCode,
                onValueChange = {
                    userPinCode = it
                },
                placeholder = {
                    Text(text = "Enter your pincode")
                })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = userAdress,
                onValueChange = {
                    userAdress = it
                },
                placeholder = {
                    Text(text = "Enter your address ")
                })
            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {
                if(userName.isEmpty() || userPassword.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty() || userPinCode.isEmpty() || userAdress.isEmpty()){

                    Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.signUp(
                        name = userName,
                        password = userPassword,
                        email = userEmail,
                        pincode = userPinCode,
                        phoneNumber = userPhone,
                        address = userAdress
                    )
                }
                



            }) {
                Text(text = "Add User ")

            }

            MultiColorText(
                firstText = "Already have a account?",
                secondText = "SignIn",
                modifier = Modifier.clickable {
                    navController.navigate(SignIn){
//                        popUpTo(SignUp) {
//                            inclusive= true
//                        }
                    }
                })
        }
    }

}