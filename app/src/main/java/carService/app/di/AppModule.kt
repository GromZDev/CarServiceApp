package carService.app.di

import carService.app.ui.auth.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    //API
//    single<Api> { RetrofitModule.apiClient}

    //repo
//    single<GetData> { GetDataImpl() }
    //vm
    viewModel { LoginViewModel(androidApplication()) }

}