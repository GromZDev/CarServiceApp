package carService.app.ui.registration

import android.app.Application
import android.util.Log
import carService.app.base.BaseViewModel
import carService.app.data.model.UserData
import carService.app.utils.CommonConstants.USER
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinApiExtension

@OptIn(KoinApiExtension::class)
class RegistrationStep2ViewModel(
    app: Application,
    private val firestore: FirebaseFirestore
) : BaseViewModel(app) {
    val newUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val isStateException = MutableStateFlow("")
    fun updateProfileUser(name: String, lastName: String, phone: String) {
        modelScope.launch {
            try {
                USER?.name = name
                USER?.lastName = lastName
                USER?.phone = phone
                updateUserProfileStep1()
                delay(1000)
            } catch (exception: TimeoutCancellationException) {
                isStateException.value = "1 - " + exception.message
                newUser.value = null

            } catch (exception: Exception) {
                isStateException.value = "2 - " + exception.message
                newUser.value = null
            }
        }
    }

    fun updateUserProfileStep1() {
        modelScope.launch {
            val user = UserData(
                uid = USER?.uid.toString(),
                name = USER?.name.toString(),
                lastName = USER?.lastName.toString(),
                nickName = USER?.nickName.toString(),
                email = USER?.email.toString(),
                phone = USER?.phone.toString(),
                lang = USER?.lang.toString(),
                type = null,
                profileImageUrl = USER?.profileImageUrl.toString(),
                location = null,
                companyServices = null,
                rating = 0f
            )

            Log.d("RegistrationStep2ViewModel", user.toString())
            val collection = firestore.collection("users")
            val document = collection.document(user.uid)
            document
                .set(user)
                .addOnSuccessListener { newUser.value = user }
                .addOnFailureListener { newUser.value = null }
                .await()
        }
    }
}