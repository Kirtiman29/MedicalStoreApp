package com.example.medicalstoreapp.UI_layer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicalstoreapp.R
import com.example.medicalstoreapp.common.MultiColorText


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestComposable(modifier: Modifier = Modifier) {

    LazyColumn(modifier=Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape))


            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = "Enter your  email")
                })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = "Enter your password ")
                })

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {  }) {
                Text(text = " Login ")
                
            }
            Spacer(modifier = Modifier.height(10.dp))
           MultiColorText(firstText = "Don't have a Account?", secondText = "SignUp", modifier = Modifier.clickable {  })
        }
    }
}