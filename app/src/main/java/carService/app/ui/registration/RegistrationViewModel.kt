package carService.app.ui.registration

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.data.model.UserData
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.Result
import carService.app.utils.SharedPreferencesHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    app: Application,
    private val prefs: SharedPreferencesHelper
) : BaseViewModel(app) {
    val newUser: MutableStateFlow<UserData?> = MutableStateFlow(null)

    fun registerByEmail(name: String, email: String, password: String) {
        modelScope.launch {
            val result = FirebaseAuthHelper.instance.registerWithEmail(name,email,password)
            when (result) {
                is Result.Success<*> -> {
                    newUser.value = result.data as? UserData
                    prefs.isAuthed = true
                    prefs.isFirstOpen = false
                }
                else -> {
                    newUser.value = null
                }
            }
        }
    }

    fun registerByGoogle(acct: GoogleSignInAccount) {
        modelScope.launch {
            val result = FirebaseAuthHelper.instance.registerWithGoogle(acct)
            when (result) {
                is Result.Success<*> -> newUser.value = result.data as? UserData
                else -> {

                }
            }
        }
    }
}