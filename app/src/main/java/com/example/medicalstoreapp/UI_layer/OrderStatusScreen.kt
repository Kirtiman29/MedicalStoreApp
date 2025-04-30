package com.example.medicalstoreapp.UI_layer

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.data_layer.AppViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderStatusScreen(
    userId: String,
    viewModel: AppViewModel = hiltViewModel(),
) {
    val orderDetailSt = viewModel.orderDetailState.collectAsState()
    var orderDetail = orderDetailSt.value.data?.body() ?: emptyList()

    LaunchedEffect(userId) {
        viewModel.getOrderDetail()
    }

    // **📌 Add Dummy Data If API Returns Empty**


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xff4294FF)),
                title = { Text(text = "Order Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(orderDetail) { order ->  // **✅ Only Show Current User’s Orders**
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EFF5))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(text = "User Name: ${order.user_name}", fontSize = 18.sp, color = Color.Black)
                        Text(text = "Product Name: ${order.product_name}", fontSize = 18.sp, color = Color.Black)
                        Text(text = "Category: ${order.category}", fontSize = 18.sp, color = Color.Black)
                        Text(text = "Price: Rs. ${order.price}", fontSize = 18.sp, color = Color.Black)
                        Text(text = "Quantity: ${order.quantity}", fontSize = 18.sp, color = Color.Black)
                        Text(text = "Total Amount: Rs. ${order.total_amount}", fontSize = 18.sp, color = Color.Black)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            if (order.isApproved == 0) {
                                Text(text = "Status: Pending", fontSize = 16.sp, color = Color.Red)
                            } else {
                                Text(text = "Status: Confirmed", fontSize = 16.sp, color = Color.Green)
                            }
                        }
                    }
                }
            }
        }
    }
}

