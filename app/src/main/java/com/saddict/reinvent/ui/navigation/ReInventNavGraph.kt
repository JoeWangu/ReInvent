package com.saddict.reinvent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saddict.reinvent.ui.screens.home.HomeDestination
import com.saddict.reinvent.ui.screens.home.HomeScreen
import com.saddict.reinvent.ui.screens.login.LoginDestination
import com.saddict.reinvent.ui.screens.login.LoginScreen
import com.saddict.reinvent.ui.screens.productdetail.ProductDetailsDestination
import com.saddict.reinvent.ui.screens.productdetail.ProductDetailsScreen

@Composable
fun ReInventNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                onLoginBtnClicked = { navController.navigate(HomeDestination.route) }
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                onNavigateUp = { navController.navigateUp() },
                navigateToItemDetails = { navController.navigate("${ProductDetailsDestination.route}/${it}") }
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg){
                type = NavType.IntType
            })
        ){
            ProductDetailsScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
