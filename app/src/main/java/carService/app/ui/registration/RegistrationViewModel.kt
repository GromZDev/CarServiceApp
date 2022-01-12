package carService.app.ui.registration

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.data.model.UserData
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.Result
import carService.app.utils.SharedPreferencesHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationViewModel(
    app: Application,
    private val prefs: SharedPreferencesHelper
) : BaseViewModel(app) {
    val newUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val isStateException = MutableStateFlow("")
    fun registerByEmail(nickName: String, email: String, password: String) {
        modelScope.launch {
            try {
                val result =
                    FirebaseAuthHelper.instance.registerWithEmail(nickName, email, password)
                when (result) {
                    is Result.Success<*> -> {
                        newUser.value = result.data as? UserData
                        prefs.isAuthed = true
                        prefs.isFirstOpen = false
                        isStateException.value = ""
                    }
                    is Result.Error -> {
                        isStateException.value = result.exception.toString()
                        newUser.value = null
                        prefs.isAuthed = false
                    }
                    is Result.Canceled -> {
                        isStateException.value = result.exception.toString()
                        newUser.value = null
                        prefs.isAuthed = false
                    }
                }
                delay(1000)
            } catch (exception: TimeoutCancellationException) {
                isStateException.value = "1 - " + exception.message
                prefs.isAuthed = false
                newUser.value = null

            } catch (exception: Exception) {
                isStateException.value = "2 - " + exception.message
                prefs.isAuthed = false
                newUser.value = null
            }
        }
    }

    fun registerByGoogle(acct: GoogleSignInAccount) {
        modelScope.launch {
            val result = FirebaseAuthHelper.instance.registerWithGoogle(acct)
            when (result) {
                is Result.Success<*> -> newUser.value = result.data as? UserData
                is Result.Error -> {
                    isStateException.value = result.exception.toString()
                    newUser.value = null
                }
                is Result.Canceled -> {
                    isStateException.value = result.exception.toString()
                    newUser.value = null
                }
            }
        }
    }
}