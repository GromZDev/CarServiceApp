package carService.app.ui.splash_screen

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.utils.FirebaseAuthHelper
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinApiExtension

class SplashScreenViewModel(
    app: Application,
) : BaseViewModel(app)  {
    val isLoggedIn = MutableStateFlow(false)

    @KoinApiExtension
    fun checkAuth() {
        FirebaseAuthHelper.instance.check {
            isLoggedIn.value = it
        }
    }
}