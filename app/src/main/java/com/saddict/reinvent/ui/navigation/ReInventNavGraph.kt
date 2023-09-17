package com.saddict.reinvent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saddict.reinvent.data.PreferenceDataStore
import com.saddict.reinvent.ui.screens.home.HomeDestination
import com.saddict.reinvent.ui.screens.home.HomeScreen
import com.saddict.reinvent.ui.screens.login.LoginDestination
import com.saddict.reinvent.ui.screens.login.LoginScreen
import com.saddict.reinvent.ui.screens.productdetail.ProductDetailsDestination
import com.saddict.reinvent.ui.screens.productdetail.ProductDetailsScreen
import com.saddict.reinvent.ui.screens.productdetail.ProductEditDestination
import com.saddict.reinvent.ui.screens.productdetail.ProductEditScreen
import com.saddict.reinvent.ui.screens.productdetail.ProductEntryDestination
import com.saddict.reinvent.ui.screens.productdetail.ProductEntryScreen

@Composable
fun ReInventNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val ctx = LocalContext.current
    val preference = PreferenceDataStore(ctx)
    val token by preference.preferenceFlow.collectAsState(initial = "")
    NavHost(
        navController = navController,
        startDestination = if (token == "") LoginDestination.route else HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) }
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                onNavigateUp = { navController.navigateUp() },
                navigateToItemDetails = { navController.navigate("${ProductDetailsDestination.route}/${it}") },
                navigateToItemEntry = { navController.navigate(ProductEntryDestination.route) }

            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg){
                type = NavType.IntType
            })
        ){
            ProductDetailsScreen(
                onNavigateUp = { navController.navigateUp() },
                navigateToEditProduct = { navController.navigate("${ProductEditDestination.route}/${it}") }
            )
        }
        composable(route = ProductEntryDestination.route){
            ProductEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ProductEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductEditDestination.productIdArg){
                type = NavType.IntType
            })
        ){
            ProductEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
