package com.example.medicalstoreapp.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun MultiColorText(firstText:String,secondText:String,modifier: Modifier=Modifier) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(firstText)
        }
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(secondText)
        }
    }

    Text(text = annotatedString, modifier = modifier)
}