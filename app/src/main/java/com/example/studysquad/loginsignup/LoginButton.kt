package com.example.studysquad.loginsignup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysquad.R

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() })
    {
        Box(modifier = Modifier
            .background(gradient)
            .width(130.dp)
            .height(45.dp)
            ,contentAlignment = Alignment.Center
        ){
            Text(text = text, color = textColor)
        }
    }
}
@Preview
@Composable
fun GradientButtonPreview(){
    GradientButton(text = "Signup", textColor = Color.White, gradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.first_color),
            colorResource(id = R.color.second_color)
        )
    ) ) {

    }
}