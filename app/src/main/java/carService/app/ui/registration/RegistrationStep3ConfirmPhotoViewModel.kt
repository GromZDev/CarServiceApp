package carService.app.ui.registration

import android.app.Application
import android.util.Log
import carService.app.base.BaseViewModel
import carService.app.data.model.UserData
import carService.app.utils.CommonConstants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationStep3ConfirmPhotoViewModel(
    app: Application,
    private val fireStore: FirebaseFirestore
) : BaseViewModel(app) {
    val newUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val isStateException = MutableStateFlow("")

    fun updateProfileUser(userImage: String) {
        modelScope.launch {
            try {
                CommonConstants.USER?.profileImageUrl = userImage

                updateUserProfileStepPhoto()
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

    private fun updateUserProfileStepPhoto() {
        modelScope.launch {
            val user = UserData(
                uid = CommonConstants.USER?.uid.toString(),
                name = CommonConstants.USER?.name.toString(),
                lastName = CommonConstants.USER?.lastName.toString(),
                nickName = CommonConstants.USER?.nickName.toString(),
                email = CommonConstants.USER?.email.toString(),
                phone = CommonConstants.USER?.phone.toString(),
                lang = CommonConstants.USER?.lang.toString(),
                type = null,
                profileImageUrl = CommonConstants.USER?.profileImageUrl.toString(),
                location = null,
                companyServices = null,
                rating = 0f
            )

            Log.d("RegistrationStep3ConfirmPhoto", user.toString())
            val collection = fireStore.collection("users")
            val document = collection.document(user.uid)
            document
                .set(user)
                .addOnSuccessListener { newUser.value = user }
                .addOnFailureListener { newUser.value = null }
                .await()
        }
    }
}