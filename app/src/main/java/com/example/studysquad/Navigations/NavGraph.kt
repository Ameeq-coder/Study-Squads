    package com.example.studysquad.Navigations

    import androidx.compose.runtime.Composable
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.example.studysquad.Chating.ChatData
    import com.example.studysquad.Chating.ChatScreen
    import com.example.studysquad.Chating.ChatViewModel
    import com.example.studysquad.Chating.MessageScreen
    import com.example.studysquad.CreateProfile.CreateProfile
    import com.example.studysquad.CreateProfile.CreateProfileViewModel
    import com.example.studysquad.di.NavViewModel
    import com.example.studysquad.loginsignup.AuthViewModel
    import com.example.studysquad.loginsignup.Login
    import com.example.studysquad.loginsignup.Signup
    import com.example.studysquad.post.PostScreen
    import com.example.studysquad.post.PostViewModel


    @Composable
    fun NavGraph(
        startDestination: String,
        authViewModel: AuthViewModel,
        navViewModel: NavViewModel,
        createProfileViewModel: CreateProfileViewModel,
        postViewModel: PostViewModel,
        chatData: ChatData,
        chatViewModel: ChatViewModel
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = startDestination) {
            composable(Route.SignupScreen.route) {
                Signup(authViewModel = authViewModel, navController = navController, navViewModel = navViewModel)
            }
            composable(Route.LoginScreen.route) {
                Login(authViewModel = authViewModel, navController = navController, navViewModel = navViewModel)
            }
            composable(Route.CreateProfile.route) {
                CreateProfile(authViewModel = authViewModel, navController = navController, navViewModel = navViewModel, createProfileViewModel)
            }
            composable(Route.PostScreen.route) {
                PostScreen(postViewModel)
            }
            composable(Route.ChatScreen.route) {
                ChatScreen(chatViewModel = chatViewModel, navViewModel = navViewModel, navController = navController)
            }
            composable(route=Route.MessageScreen.route) {
                val otherUserId = it.arguments?.getString("otherUserId")
                otherUserId?.let {
                    MessageScreen(navController=navController, otherUserId=otherUserId ,chatViewModel=chatViewModel)
                }
            }
        }
    }

