package com.example.studysquad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.studysquad.Navigations.NavGraph
import com.example.studysquad.Navigations.Route
import com.example.studysquad.di.NavViewModel
import com.example.studysquad.loginsignup.AuthViewModel
import com.example.studysquad.loginsignup.Login
import com.example.studysquad.loginsignup.Signup
import com.example.studysquad.ui.theme.StudySquadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySquadTheme {
                // A surface container using the 'background' color from the theme
                val authviewModel: AuthViewModel = viewModel()
                val navViewModel:NavViewModel=viewModel()
                val navController = rememberNavController()
                NavGraph(startDestination = Route.LoginScreen.route, authViewModel = authviewModel, navViewModel = navViewModel)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudySquadTheme {

    }
}