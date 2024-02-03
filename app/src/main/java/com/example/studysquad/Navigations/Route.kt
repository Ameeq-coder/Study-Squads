package com.example.studysquad.Navigations

sealed class Route(
    val route: String
) {
object LoginScreen:Route(route = "login_screen")

object SignupScreen:Route(route = "signup_screen")

}