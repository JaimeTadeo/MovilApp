package com.example.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.feature.main.application.MainScreen
import com.example.app.feature.profile.github.application.GitHubScreen
import com.example.app.feature.profile.login.application.LoginScreen
import com.example.app.feature.profile.login.application.ForgotPasswordScreen
import com.example.app.features.movies.presentation.MoviesScreen

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                modifier = Modifier,
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPasswordScreen.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(
                modifier = Modifier,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.MainScreen.route) {
            MainScreen(
                modifier = Modifier,
                onNavigateToGithub = {
                    navController.navigate(Screen.GithubScreen.route)
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                onNavigateToMovies = {
                    navController.navigate(Screen.MoviesScreen.route)
                },
                onNavigateToDollar = {
                    navController.navigate(Screen.DollarScreen.route)
                },
                onNavigateToMessaging = {
                    navController.navigate(Screen.MessagingScreen.route)
                }
            )
        }

        composable(Screen.GithubScreen.route) {
            GitHubScreen(
                modifier = Modifier,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.MoviesScreen.route) {
            MoviesScreen()
        }

        composable(Screen.DollarScreen.route) {
            com.example.app.features.dollar.DollarScreen()
        }

        composable(Screen.MessagingScreen.route) {
            com.example.app.features.messaging.presentation.MessagingScreen()
        }
    }
}