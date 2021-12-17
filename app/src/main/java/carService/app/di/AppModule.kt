package carService.app.di

import carService.app.ui.auth.LoginViewModel
import carService.app.utils.AppImageView
import carService.app.utils.SharedPreferencesHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    //API
//    single<Api> { RetrofitModule.apiClient}

    //utils
    single { SharedPreferencesHelper(androidApplication()) }

    // repo
    factory { AppImageView() }

    //repo
//    single<GetData> { GetDataImpl() }
    //vm
    viewModel { LoginViewModel(androidApplication()) }

}