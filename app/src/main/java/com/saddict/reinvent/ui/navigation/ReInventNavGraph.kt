package com.saddict.reinvent.ui.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saddict.reinvent.products.data.manager.PreferenceDataStore
import com.saddict.reinvent.ui.screens.extra.LoadingDestination
import com.saddict.reinvent.ui.screens.extra.ScreenLoading
import com.saddict.reinvent.ui.screens.home.HomeDestination
import com.saddict.reinvent.ui.screens.home.HomeScreen
import com.saddict.reinvent.ui.screens.registration.LoginDestination
import com.saddict.reinvent.ui.screens.registration.LoginScreen
import com.saddict.reinvent.ui.screens.productdetail.ProductDetailsDestination
import com.saddict.reinvent.ui.screens.productdetail.ProductDetailsScreen
import com.saddict.reinvent.ui.screens.productdetail.ProductEditDestination
import com.saddict.reinvent.ui.screens.productdetail.ProductEditScreen
import com.saddict.reinvent.ui.screens.productdetail.ProductEntryDestination
import com.saddict.reinvent.ui.screens.productdetail.ProductEntryScreen
import com.saddict.reinvent.ui.screens.registration.RegisterDestination
import com.saddict.reinvent.ui.screens.registration.RegisterScreen
import com.saddict.reinvent.utils.toastUtil
import kotlinx.coroutines.flow.first

@Composable
fun ReInventNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    var pressedTime: Long = 0
    val ctx = LocalContext.current
    val preference = PreferenceDataStore(ctx)
    val activity = LocalContext.current as? Activity
    LaunchedEffect(key1 = Unit) {
        val token = preference.preferenceFlow.first()
        if (token.isNotBlank()) {
            navController.navigate(HomeDestination.route) {
                popUpTo(LoadingDestination.route) { inclusive = true }
            }
        } else {
            navController.navigate(LoginDestination.route) {
                popUpTo(LoadingDestination.route) { inclusive = true }
            }
        }
    }
    val tokenLot = preference.getToken()
    NavHost(
        navController = navController,
        startDestination = if (tokenLot == "") LoginDestination.route else HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = LoadingDestination.route) {
            ScreenLoading()
        }
        composable(route = LoginDestination.route) {
            BackHandler {
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    activity?.finish()
                } else {
                    ctx.toastUtil("Press back again to exit")
                }
                pressedTime = System.currentTimeMillis()
            }
            LoginScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToRegister = { navController.navigate(RegisterDestination.route) }
            )
        }
        composable(route = HomeDestination.route) {
            BackHandler {
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    activity?.finish()
                } else {
                    ctx.toastUtil("Press back again to exit")
                }
                pressedTime = System.currentTimeMillis()
            }
            HomeScreen(
                navigateToItemDetails = { navController.navigate("${ProductDetailsDestination.route}/${it}") },
                navigateToItemEntry = { navController.navigate(ProductEntryDestination.route) },
                navigateToLogin = { navController.navigate(LoginDestination.route) }
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg){
                type = NavType.IntType
            })
        ){
            ProductDetailsScreen(
                navigateBack = { navController.navigateUp() },
                navigateToEditProduct = { navController.navigate("${ProductEditDestination.route}/${it}") }
            )
        }
        composable(route = ProductEntryDestination.route){
            ProductEntryScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ProductEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductEditDestination.productIdArg){
                type = NavType.IntType
            })
        ){
            ProductEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = RegisterDestination.route) {
            RegisterScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
