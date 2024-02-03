package com.example.studysquad.Navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.studysquad.loginsignup.AuthViewModel
import com.example.studysquad.loginsignup.Login
import com.example.studysquad.loginsignup.Signup

@Composable
fun NavGraph(
    startDestination: String
) {
    val navcontroller = rememberNavController()
    NavHost(navController = navcontroller, startDestination = startDestination) {
        navigation(route = Route.SignupScreen.route, startDestination = Route.SignupScreen.route) {
            composable(route=Route.SignupScreen.route){
                val navController= rememberNavController()
                val viewmodel:AuthViewModel= viewModel()
                NavHost(navController = navController, startDestination =startDestination){
                composable(route=Route.SignupScreen.route){
                    Signup(authViewModel = viewmodel,navController=navController)
                }
composable(route=Route.LoginScreen.route){
    Login(authViewModel = viewmodel,navController=navController)
}

                }

            }
        }
    }
}