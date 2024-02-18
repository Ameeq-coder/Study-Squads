package com.example.studysquad.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysquad.R
import com.example.studysquad.loginsignup.GradientButton

@Composable
fun PostScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier.padding(top = 13.dp, start = 10.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyLarge  .copy(fontWeight = FontWeight.Bold),
                text = "Create Post ",
                textAlign = TextAlign.Center
            )
            PostButton(text = "Post", textColor = Color.White, gradient = Brush.verticalGradient(
                colors = listOf(
                    colorResource(id = R.color.postcolorone),
                    colorResource(id = R.color.postcolortwo)
                )
            ) ) {

            }
            
        }




    }

}


@Composable
@Preview(showBackground = true)
fun PostScreenPreview() {
    PostScreen()
}