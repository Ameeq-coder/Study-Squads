package com.example.studysquad.Navigations


sealed class Route(
    val route: String
) {
    object LoginScreen : Route(route = "login_screen")

    object SignupScreen : Route(route = "signup_screen")

    object CreateProfile : Route(route = "create_screen")

    object PostScreen : Route(route = "post_screen")

    object ChatScreen: Route(route = "chat_screen")

    object MessageScreen:Route(route = "message_screen/{otherUserId}"){
        fun createRoute(otherUserId:String)="message_screen/$otherUserId"
    }
}