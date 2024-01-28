package com.example.studysquad.loginsignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.studysquad.R
import com.example.studysquad.ui.theme.StudySquadTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Signup(authViewModel: AuthViewModel){
    Column(modifier = Modifier.fillMaxSize()) {
      Box(modifier = Modifier, contentAlignment = Alignment.Center){
          Image(painter = painterResource(id = R.drawable.loginsignup), contentDescription = null )
      }
    }

}
@Preview(showBackground = true)
@Composable
fun SignupPreview(){
    StudySquadTheme {
        Signup(authViewModel = AuthViewModel(firebaseAuth = FirebaseAuth.getInstance())) // Pass the required parameters to Signup function
    }

}