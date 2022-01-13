package carService.app.ui.splash_screen

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.data.model.UserData
import carService.app.utils.FirebaseAuthHelper
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class SplashScreenViewModel(
    app: Application,
) : BaseViewModel(app)  {
    val isLoggedIn = MutableStateFlow(false)
    val userData: MutableStateFlow<UserData?> =  MutableStateFlow(null)

    @KoinApiExtension
    fun loadUser() {
        userData.value = FirebaseAuthHelper.instance.getUser()
    }

    @KoinApiExtension
    fun checkAuth() {
        FirebaseAuthHelper.instance.check {
            isLoggedIn.value = it
        }
    }
}