package com.example.medicalstoreapp.UI_layer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.medicalstoreapp.data_layer.AppViewModel


@Composable
fun TemporaryScreen(navController: NavHostController, viewModel: AppViewModel = hiltViewModel()) {


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "You Are Not Verified ",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic)
    }

}


@Preview(showBackground = true)
@Composable
fun previewScreen() {
    TemporaryScreen(navController = NavHostController(LocalContext.current), viewModel = hiltViewModel())


}