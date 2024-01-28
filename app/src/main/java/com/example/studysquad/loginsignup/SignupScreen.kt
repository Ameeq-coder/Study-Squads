package com.example.studysquad.loginsignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysquad.R
import com.example.studysquad.ui.theme.StudySquadTheme

@Composable
fun Signup(){
    Column(modifier = Modifier
        .background(Color.White)
        .fillMaxSize(),) {
      Box(modifier = Modifier.padding(25.dp), contentAlignment = Alignment.Center){
          Image(painter = painterResource(id = R.drawable.loginsignup), contentDescription = null )
      }
      Text(text = "Study Squads")
    }

}
@Preview(showBackground = true)
@Composable
fun SignupPreview(){
    StudySquadTheme {
        Signup() // Pass the required parameters to Signup function
    }

}