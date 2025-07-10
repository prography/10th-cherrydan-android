package com.hyunjung.feature.auth.di

import com.hyunjung.feature.auth.login.LogInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authViewModelModule = module {
    viewModel { LogInViewModel(get()) }
}