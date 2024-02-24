package com.example.studysquad.Navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.studysquad.CreateProfile.CreatePost
import com.example.studysquad.CreateProfile.CreateProfileViewModel
import com.example.studysquad.di.NavViewModel
import com.example.studysquad.loginsignup.AuthViewModel
import com.example.studysquad.loginsignup.Login
import com.example.studysquad.loginsignup.Signup


@Composable
fun NavGraph(startDestination: String,authViewModel: AuthViewModel,navViewModel: NavViewModel,createProfileViewModel: CreateProfileViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Route.SignupScreen.route) {
            Signup(authViewModel = authViewModel, navController = navController, navViewModel = navViewModel)
        }
        composable(Route.LoginScreen.route) {
            Login(authViewModel = authViewModel, navController = navController, navViewModel = navViewModel)
        }
composable(Route.CreatePost.route){
    CreatePost(authViewModel = authViewModel,navController=navController,navViewModel=navViewModel,createProfileViewModel)
}
    }
}