package com.example.medicalstoreapp.UI_layer

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    userId: String,
    productId: String,
    productName: String,
    price: String,
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val orderState by viewModel.orderState.collectAsState()
    val specificProductState by viewModel.getSpecificProductState.collectAsState()
    val showSpecificData = specificProductState.data?.body()

    var quantity by remember { mutableStateOf(1) }
    var quantityText by remember { mutableStateOf(quantity.toString()) }
    var showDialog by remember { mutableStateOf(false) } // Controls popup visibility

    // Fetch product details when screen opens
    LaunchedEffect(productId) {
        viewModel.getSpecificProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Medical App",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .size(350.dp), // Increased size for better layout
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EFF5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // Add slight shadow
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Title
                        Text(
                            text = "Order Summary",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A237E) // Dark Blue Color
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Product Name
                        Text(
                            text = "Product Name: $productName",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Price
                        Text(
                            text = "Price: $price",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF00897B) // Teal Color
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Number Picker (Spinner with Manual Input)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    if (quantity > 1) {
                                        quantity -= 1
                                        quantityText = quantity.toString()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)), // Dark Blue
                                modifier = Modifier.size(50.dp)
                            ) {
                                Text(text = "-", fontSize = 25.sp, color = Color.White)
                            }

                            OutlinedTextField(
                                value = quantityText,
                                onValueChange = {
                                    val newQuantity = it.toIntOrNull()
                                    if (newQuantity != null && newQuantity > 0) {
                                        quantity = newQuantity
                                        quantityText = it
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .size(70.dp, 50.dp), // Compact size
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                            )

                            Button(
                                onClick = {
                                    quantity += 1
                                    quantityText = quantity.toString()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)), // Dark Blue
                                modifier = Modifier.size(50.dp)
                            ) {
                                Text(text = "+", fontSize = 25.sp, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Update Quantity Button
                        Button(
                            onClick = {
                                if (quantity <= 0) {
                                    Toast.makeText(
                                        context,
                                        "Please enter a valid quantity",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    viewModel.addOrder(
                                        user_id = userId,
                                        product_id = productId,
                                        product_name = productName,
                                        quantity = quantity,
                                        total_amount = price.toFloatOrNull()?.times(quantity) ?: 0.0f,
                                        price = price,
                                        category = "Medicine",
                                        user_name = "Saktiman",
                                        message = "Order placed"
                                    )
                                    showDialog = true // Show popup when order is placed
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00897B)), // Teal Green
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(48.dp)
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

    // **Popup Dialog**
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.padding(20.dp), // Add padding for better spacing
            containerColor = Color(0xFFFFFFFF), // White background
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Success Icon
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Success",
                        tint = Color(0xFF4CAF50), // Green color
                        modifier = Modifier.size(50.dp) // Adjust icon size
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Order Confirmation Title
                    Text(
                        text = "Order Confirmed",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A237E) // Dark Blue Color
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your order has been placed successfully!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Thank you for shopping with us!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                            navController.popBackStack() // Navigate back after order placement
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green color
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "OK",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }

}
