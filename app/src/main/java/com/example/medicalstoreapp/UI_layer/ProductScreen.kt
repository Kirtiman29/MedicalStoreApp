package com.example.medicalstoreapp.UI_layer

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.data_layer.AppViewModel
import com.example.medicalstoreapp.navigation.OrderProduct


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavHostController, viewModel: AppViewModel = hiltViewModel()) {
    val state = viewModel.getAllProductState.collectAsState()
    val showData = state.value.data?.body() ?: emptyList()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Medical Store",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2)), // Blue
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.value.loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.error != null -> {
                Toast.makeText(context, "${state.value.error}", Toast.LENGTH_SHORT).show()
            }

            state.value.data != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(showData) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(
                                        Screen.GetProduct.createRoute(
                                            product.product_id,
                                            product.product_name,
                                            product.price.toString()
                                        )
                                    )
                                },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Product Name
                                Text(
                                    text = product.product_name,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF004080)
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                // Product Price
                                Text(
                                    text = "Price: Rs. ${product.price}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF00897B) // Teal Color
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                // Product Category
                                Text(
                                    text = "Category: ${product.category}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Light,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                // Expiry Date
                                Text(
                                    text = "Expiry Date: ${product.expire_date}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Light,
                                    color = Color.Red
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                // Stock Information
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Stock Available:",
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = product.stock.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (product.stock > 0) Color.Green else Color.Red
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                // Order Button
                                Button(
                                    onClick = {
                                        navController.navigate(
                                            Screen.GetProduct.createRoute(
                                                product.product_id,
                                                product.product_name,
                                                product.price.toString()
                                            )
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // Blue Color
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .height(45.dp)
                                ) {
                                    Text(
                                        text = "Order Now",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
