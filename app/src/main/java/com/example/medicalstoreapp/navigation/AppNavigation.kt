package com.example.medicalstoreapp.navigation

import UserProfileScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicalstoreapp.UI_layer.HomeScreen
import com.example.medicalstoreapp.UI_layer.OrderScreen
import com.example.medicalstoreapp.UI_layer.OrderStatusScreen
import com.example.medicalstoreapp.UI_layer.ProductScreen
import com.example.medicalstoreapp.UI_layer.SignInUI
import com.example.medicalstoreapp.UI_layer.SignUpUI
import com.example.medicalstoreapp.UI_layer.SplashScreen
import com.example.medicalstoreapp.UI_layer.TemporaryScreen
import com.example.medicalstoreapp.data_layer.userPrefrence.UserPrefrenceManager
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    userPrefrenceManager: UserPrefrenceManager
) {
    val navController = rememberNavController()

    val userId by userPrefrenceManager.user_id.collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(userId) {
        if (userId != null) {
            navController.navigate(ProductDetails)
        } else {
            navController.navigate(SignIn)
        }
    }
    //val startDestination = if (userId != null) Temporary else SignIn

    Box {
        val items = listOf(
            BottomNavigationItem(
                title = "Products",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            BottomNavigationItem(
                title = "Order",
                selectedIcon = Icons.Filled.ShoppingCart,
                unselectedIcon = Icons.Outlined.ShoppingCart
            ),
            BottomNavigationItem(
                title = "Profile",
                selectedIcon = Icons.Filled.AccountBox,
                unselectedIcon = Icons.Outlined.AccountBox
            )
        )

        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        Scaffold (

            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF45B6FE),
                    modifier = Modifier.height(90.dp)
                ) {
                    items.forEachIndexed { index,item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                coroutineScope.launch {
                                    when(index){
                                        0 -> navController.navigate(ProductDetails)
                                        1 -> navController.navigate(Screen2.getUserID.route)
                                        2 -> navController.navigate(Profile)
                                    }
                                }


                            },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                if(selectedItemIndex == index){
                                    Icon(
                                        imageVector = item.selectedIcon,
                                        contentDescription = item.title
                                    )
                                }else{
                                    Icon(
                                        imageVector = item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }

                }

            }
        ){innerPadding ->
            Box(modifier = Modifier.padding(innerPadding))
            NavHost(navController = navController, startDestination = Profile) {
                composable<StartScreen> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
//            val userLogin = false
//            if (userLogin) {
//                navController.navigate(HomeScreen)
//            } else {
//                navController.navigate(SignIn)
//            }
                }
                composable<Splash>{
                    SplashScreen(viewModel = hiltViewModel(), navHostController = navController, userPrefrenceManager)
                }

                composable<SignIn> {
                    SignInUI(navController)
                }
                composable<SignUp> {
                    SignUpUI(navController)
                }
                composable<Home> {
                    HomeScreen(navController)
                }
                composable<Temporary> {

                    TemporaryScreen(navController)
                }
                composable<ProductDetails> {
                    ProductScreen(navController)
                }

                composable(Screen.GetProduct.route) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId") ?: ""
                    val productName = backStackEntry.arguments?.getString("productName") ?: ""
                   // val quantity = backStackEntry.arguments?.getString("quantity")?: ""
                    val price = backStackEntry.arguments?.getString("price") ?: ""

                    OrderScreen(
                        productId = productId,
                        productName = productName,
                        price = price,
                        userId = userId.toString(),
                        navController = navController
                    )
                }


                composable<Profile> {
                        UserProfileScreen(navHostController = navController,
                            userPreferenceManager = userPrefrenceManager)
                }

                composable(Screen2.getUserID.route) {backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")
                    OrderStatusScreen(
                        userId = userId.toString()
                    )
                }

            }

        }


    }





}
data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)