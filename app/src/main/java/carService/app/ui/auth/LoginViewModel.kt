package carService.app.ui.auth

import android.app.Application
import android.util.Log
import carService.app.base.BaseViewModel
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.Result
import carService.app.utils.SharedPreferencesHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinApiExtension
import java.lang.Thread.sleep

@KoinApiExtension
class LoginViewModel(
    app: Application,
    private val prefs: SharedPreferencesHelper
) : BaseViewModel(app) {
    val isLoggedIn = MutableStateFlow(false)
    val isStateException = MutableStateFlow("")

    private val _stateInitial = MutableStateFlow(false)
    val stateInitial = _stateInitial.asStateFlow()

    val timeout = 1500L

    fun loginByEmail(email: String, password: String) {
        modelScope.launch {
            withTimeout(timeout) {
                try {
                    val result = FirebaseAuthHelper.instance.loginUserByEmail(email, password)
                    when (result) {
                        is Result.Success<*> -> {
                            isLoggedIn.value = true
                            prefs.isAuthed = true
                            prefs.isFirstOpen = false
                            isStateException.value = ""
                            _stateInitial.emit(true)
                        }
                        is Result.Error -> {
                            isStateException.value = result.exception.toString()
                            prefs.isAuthed = false
                            isLoggedIn.value = false
                        }
                        is Result.Canceled -> {
                            isStateException.value = result.exception.toString()
                            prefs.isAuthed = false
                            isLoggedIn.value = false
                        }
                    }
                    delay(1000)
                } catch (exception: TimeoutCancellationException) {
                    isStateException.value = "1 - " + exception.message
                    prefs.isAuthed = false
                    isLoggedIn.value = false

                } catch (exception: Exception) {
                    isStateException.value = "2 - " + exception.message
                    prefs.isAuthed = false
                    isLoggedIn.value = false
                }
            }
        }
    }

    fun loginByGoogle(acct: GoogleSignInAccount) {
        modelScope.launch {
            val result = FirebaseAuthHelper.instance.loginUserByGoogle(acct)
            when (result) {
                is Result.Success<*> -> {
                    isLoggedIn.value = true
                    prefs.isAuthed = true
                    _stateInitial.emit(true)
                }
                is Result.Error -> {
                    isStateException.value = result.exception.toString()
                    isLoggedIn.value = false
                }
                is Result.Canceled -> {
                    isStateException.value = result.exception.toString()
                    isLoggedIn.value = false
                }
            }
        }
    }

    fun checkAuth() {
        FirebaseAuthHelper.instance.check {
            isLoggedIn.value = it
        }
    }
}