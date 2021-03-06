package com.adyen.android.assignment.ui

import com.adyen.android.assignment.ui.viewmodel.AstronomyListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { AstronomyListViewModel(get(), get()) }
    single { ConnectionStatusProvider(androidContext()) }
}