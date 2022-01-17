package carService.app.di

import carService.app.repo.personal.Repository
import carService.app.repo.personal.UserRepository
import carService.app.ui.auth.ForgotPasswordViewModel
import carService.app.ui.auth.LoginViewModel
import carService.app.ui.main.main_screen.personal_account.MainUserViewModel
import carService.app.ui.main.menu_screens.company_menu.more_company_menu.MoreCompanyMenuViewModel
import carService.app.ui.main.menu_screens.personal_menu.more_menu.MoreMenuViewModel
import carService.app.ui.registration.*
import carService.app.ui.splash_screen.SplashScreenViewModel
import carService.app.utils.AppImageView
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.SharedPreferencesHelper
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val appModule = module {

    //API
//    single<Api> { RetrofitModule.apiClient}

    //FireBase
    single { FirebaseApp.initializeApp(androidApplication()) }
    factory { FirebaseAuth.getInstance().currentUser }
    single { FirebaseAuth.getInstance() }
    factory { FirebaseDatabase.getInstance().reference }
    single { FirebaseFirestore.getInstance() }
    factory { FirebaseStorage.getInstance().reference }
    single { FirebaseStorage.getInstance() }
    single { FirebaseMessaging.getInstance() }
    single { FireBaseModule(androidApplication()) }

    //utils
    single { SharedPreferencesHelper(androidApplication()) }
    single { FirebaseAuthHelper() }

    // repo
    factory { AppImageView() }
    factory { UserRepository() }
    single { Repository(get()) }
    //repo
//    single<GetData> { GetDataImpl() }
    //vm
    viewModel { LoginViewModel(androidApplication(), get()) }
    viewModel { RegistrationViewModel(androidApplication(), get()) }
    viewModel { MoreCompanyMenuViewModel(androidApplication(), get()) }
    viewModel { MoreMenuViewModel(androidApplication(), get()) }
    viewModel { SplashScreenViewModel(androidApplication()) }
    viewModel { RegistrationStep2ViewModel(androidApplication(), get()) }
    viewModel { MainUserViewModel(androidApplication()) }
    viewModel { RegistrationStep3ConfirmPhotoViewModel(androidApplication(), get()) }
    viewModel { RegistrationStep4LocationViewModel(androidApplication(), get()) }
    viewModel { ForgotPasswordViewModel(androidApplication()) }
    viewModel { RegistrationStep5RoleViewModel(androidApplication(), get()) }
}