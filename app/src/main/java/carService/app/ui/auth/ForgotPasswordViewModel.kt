package carService.app.ui.auth

import android.app.Application
import android.text.TextUtils
import carService.app.base.BaseViewModel
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.Result
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ForgotPasswordViewModel(app: Application
) : BaseViewModel(app) {
    val isSendPasswordResetEmail = MutableStateFlow(false)
    val isUpdatePassword = MutableStateFlow(false)
    val isStateException = MutableStateFlow("")

    @KoinApiExtension
    fun sendPasswordResetEmail(emailAddress: String) {
        modelScope.launch {
            try {
                val result = FirebaseAuthHelper.instance.sendPasswordResetEmail(emailAddress)
                    when (result) {
                        is Result.Success<*> -> {
                            isSendPasswordResetEmail.value = true
                        }
                        is Result.Error -> {
                            isStateException.value = result.exception.toString()
                            isSendPasswordResetEmail.value = false
                        }
                        is Result.Canceled -> {
                            isStateException.value = result.exception.toString()
                            isSendPasswordResetEmail.value = false
                        }
                    }
                delay(1000)
            } catch (exception: TimeoutCancellationException) {
                isStateException.value = "1 - " + exception.message
                isSendPasswordResetEmail.value = false
            } catch (exception: Exception) {
                isStateException.value = "2 - " + exception.message
                isSendPasswordResetEmail.value = false
            }
        }
    }

    fun updatePassword(newPassword: String){
        modelScope.launch {
            val result = FirebaseAuthHelper.instance.updatePassword(newPassword)
            when (result) {
                is Result.Success<*> -> {
                    isUpdatePassword.value = true
                }
                is Result.Error -> {
                    isStateException.value = result.exception.toString()
                    isUpdatePassword.value = false
                }
                is Result.Canceled -> {
                    isStateException.value = result.exception.toString()
                    isUpdatePassword.value = false
                }
            }
        }
    }
}