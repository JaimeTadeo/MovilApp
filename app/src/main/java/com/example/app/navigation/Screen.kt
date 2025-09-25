package com.example.app.navigation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login")
    object ForgotPasswordScreen: Screen("forgot_password")
    object GithubScreen: Screen("github")
    object MainScreen: Screen("main")
    object MoviesScreen: Screen("movies")
    object DollarScreen: Screen("dollar")
    object MessagingScreen: Screen("messaging")
}