package com.isacetin.jopposting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.isacetin.jopposting.scene.home.navigateHome
import com.isacetin.jopposting.scene.login.LOGIN
import com.isacetin.jopposting.scene.login.login
import com.isacetin.jopposting.scene.login.navigateLogin
import com.isacetin.jopposting.scene.register.register
import com.isacetin.jopposting.scene.register.routeRegister
import com.isacetin.jopposting.scene.splash.SPLASH
import com.isacetin.jopposting.scene.splash.splash

fun NavGraphBuilder.authGraph(navController: NavController) {
    splash(
        navigateToHome = {
            navController.popBackStack(SPLASH, true)
            navController.navigateHome()
        },
        navigateToLogin = {
            navController.popBackStack(SPLASH, true)
            navController.navigateLogin()
        }
    )

    login(
        onNavigateToRegister = {
            navController.routeRegister()
        },
        onNavigateToHome = {
            navController.popBackStack(LOGIN, true)
            navController.navigateHome()
        }
    )

    register(
        onNavigationClick = {
            navController.popBackStack()
        }
    )
}
