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
import com.example.studysquad.Chating.ChatData
import com.example.studysquad.Chating.ChatScreen
import com.example.studysquad.Chating.ChatViewModel
import com.example.studysquad.CreateProfile.CreateProfileViewModel
import com.example.studysquad.Navigations.NavGraph
import com.example.studysquad.Navigations.Route
import com.example.studysquad.di.NavViewModel
import com.example.studysquad.loginsignup.AuthViewModel
import com.example.studysquad.loginsignup.Login
import com.example.studysquad.loginsignup.Signup
import com.example.studysquad.post.PostViewModel
import com.example.studysquad.ui.theme.StudySquadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySquadTheme {
                val chatData = ChatData(/* pass necessary parameters here */)
                val authviewModel: AuthViewModel = viewModel()
                val navViewModel:NavViewModel=viewModel()
                val createprofileviewmodel:CreateProfileViewModel= viewModel()
                val postViewModel:PostViewModel= viewModel()
                val chatViewModel: ChatViewModel = viewModel()
                NavGraph(
                    startDestination = Route.SignupScreen.route,
                    authViewModel = authviewModel,
                    navViewModel = navViewModel,
                    createProfileViewModel = createprofileviewmodel, // Specify parameter name
                    postViewModel = postViewModel, // Specify parameter name
                    chatViewModel = chatViewModel,
                    chatData = chatData
                )
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