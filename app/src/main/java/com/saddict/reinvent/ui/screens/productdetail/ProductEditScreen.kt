package com.saddict.reinvent.ui.screens.productdetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saddict.reinvent.R
import com.saddict.reinvent.products.data.manager.AppUiState
import com.saddict.reinvent.ui.TopBar
import com.saddict.reinvent.ui.navigation.NavigationDestination
import com.saddict.reinvent.ui.screens.AppViewModelProvider
import com.saddict.reinvent.utils.toastUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ProductEditDestination : NavigationDestination {
    override val route: String = "product_edit"
    override val titleRes: Int = R.string.product_edit_title
    const val productIdArg = "productId"
    val routeWithArgs = "$route/{$productIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: ProductEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val ctx = LocalContext.current
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = ProductEditDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ProductEntryBody(
            productEntryUiState = viewModel.productEditUiState,
            onProductValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateProduct()
                    viewModel.uiState.collect{ state ->
                        when(state){
                            AppUiState.Error -> {
                                ctx.toastUtil("Could not save")
                                navigateBack()
                            }
                            AppUiState.Loading -> ctx.toastUtil("Saving Product")
                            is AppUiState.Success -> {
                                ctx.toastUtil("Updated Successfully")
                                delay(2_000L)
                                navigateBack()
                            }
                        }
                    }
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}