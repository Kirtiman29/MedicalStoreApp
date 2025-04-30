import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.R
import com.example.medicalstoreapp.data_layer.AppViewModel
import com.example.medicalstoreapp.data_layer.response.getSpecificUserResponce
import com.example.medicalstoreapp.data_layer.userPrefrence.UserPrefrenceManager
import com.example.medicalstoreapp.navigation.SignIn
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    viewModel: AppViewModel = hiltViewModel(),
    navHostController: NavHostController,
    userPreferenceManager: UserPrefrenceManager,
) {
    val getSpecificUserState = viewModel.getSpecificUserState.collectAsState()
    var showDialog by remember { mutableStateOf(false) } // Logout confirmation dialog

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2))
            )
        },
//        bottomBar = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Button(
//                    onClick = { navHostController.navigate("EditProfileScreen") },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(12.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
//                ) {
//                    Text("Edit Profile", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
//                }
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//                Button(
//                    onClick = { showDialog = true },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(12.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
//                ) {
//                    Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
//                }
//            }
//        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // **Profile Image**
                Card(
                    shape = RoundedCornerShape(50.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.img),
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(120.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // **User Information**
            when {
                getSpecificUserState.value.loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                getSpecificUserState.value.error != null -> {
                    item { Text("Failed to load user. Please try again.", color = Color.Red) }
                }
                getSpecificUserState.value.data != null -> {
                    val user = getSpecificUserState.value.data!!

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val userFields = listOf(
                                    "Username" to (user.body()?.name ?: "Kirtiman Gupta"),
                                    "Email" to (user.body()?.email ?: "kirtiman@gmail.com"),
                                    "Phone Number" to (user.body()?.phone_number ?: "9372860830"),
                                    "Address" to (user.body()?.address ?: "Powai, Mumbai"),
                                    "PinCode" to (user.body()?.pincode ?: "400076"),
                                    "Account Created On" to (user.body()?.date_of_account_creation ?: "N/A")
                                )

                                userFields.forEach { (label, value) ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Text(text = label, fontSize = 14.sp, color = Color.Gray)
                                        Text(
                                            text = value,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1976D2)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
                            }
                        }
                    }
                }
            }
        }
    }

    // **Logout Confirmation Dialog**
    if (showDialog) {
        val coroutineScope = rememberCoroutineScope() // Define coroutine scope

        LogoutDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                coroutineScope.launch {
                    userPreferenceManager.clearUserID()
                    navHostController.navigate(SignIn) {
                        popUpTo(0)
                    }
                }
                showDialog = false
            }
        )
    }

}
@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }
        },
        text = {
            Text(
                "Are you sure you want to logout?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Yes, Logout", fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", fontWeight = FontWeight.SemiBold, color = Color.Gray)
            }
        }
    )
}

