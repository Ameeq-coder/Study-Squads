package com.example.studysquad.Navigations

sealed class Route(
    val route: String
) {
object LoginScreen:Route(route = "login_screen")

object SignupScreen:Route(route = "signup_screen")

object CreateProfile:Route(route = "create_screen")

object PostScreen:Route(route = "post_screen")
}