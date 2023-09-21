package com.saddict.reinvent.ui.screens

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.saddict.reinvent.ReInventApplication
import com.saddict.reinvent.ui.screens.home.HomeViewModel
import com.saddict.reinvent.ui.screens.registration.LoginViewModel
import com.saddict.reinvent.ui.screens.productdetail.ProductDetailsViewModel
import com.saddict.reinvent.ui.screens.productdetail.ProductEditViewModel
import com.saddict.reinvent.ui.screens.productdetail.ProductEntryViewModel
import com.saddict.reinvent.ui.screens.registration.RegisterViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer HomeViewModel
        initializer {
            HomeViewModel(
                reInventApplication().container.daoRepositoryInt,
                context = reInventApplication().applicationContext
            )
        }
        initializer {
            ProductDetailsViewModel(
                this.createSavedStateHandle(),
                reInventApplication().container.daoRepositoryInt
            )
        }
        initializer {
            ProductEntryViewModel(
                context = reInventApplication().applicationContext
            )
        }
        initializer {
            ProductEditViewModel(
                context = reInventApplication().applicationContext,
                this.createSavedStateHandle(),
                reInventApplication().container.daoRepositoryInt
            )
        }
        initializer {
            LoginViewModel(
                context = reInventApplication().applicationContext

            )
        }
        initializer {
            RegisterViewModel(
                context = reInventApplication().applicationContext
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [ReInventApplication].
 */
fun CreationExtras.reInventApplication(): ReInventApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ReInventApplication)
