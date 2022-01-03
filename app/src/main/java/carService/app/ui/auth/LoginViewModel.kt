package carService.app.ui.auth

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.Result
import carService.app.utils.SharedPreferencesHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    app: Application,
    private val prefs: SharedPreferencesHelper
) : BaseViewModel(app) {
    val isLoggedIn = MutableStateFlow(false)


    fun loginByEmail(email: String, password: String) {
        modelScope.launch {
            val result =  FirebaseAuthHelper.instance.loginUserByEmail(email,password)
            when (result) {
                is Result.Success<*> -> {
                    isLoggedIn.value = true
                    prefs.isAuthed = true
                    prefs.isFirstOpen = false
                }
                else -> isLoggedIn.value = false
            }
        }
    }

    fun loginByGoogle(acct: GoogleSignInAccount) {
        modelScope.launch {
            val result =  FirebaseAuthHelper.instance.loginUserByGoogle(acct)
            when (result) {
                is Result.Success<*> -> {
                    isLoggedIn.value = true
                    prefs.isAuthed = true
                    prefs.isFirstOpen = false
                }
                else -> isLoggedIn.value = false
            }
        }
    }

    fun checkAuth() {
        FirebaseAuthHelper.instance.check {
            isLoggedIn.value = it
        }
    }
}