package carService.app.ui.registration

import android.app.Application
import android.util.Log
import carService.app.base.BaseViewModel
import carService.app.data.model.Location
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
class RegistrationStep4LocationViewModel(
    app: Application,
    private val fireStore: FirebaseFirestore
) : BaseViewModel(app) {
    val newUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val isStateException = MutableStateFlow("")

    fun updateProfileUser(userLocation: Location) {
        modelScope.launch {
            try {
                CommonConstants.USER?.location = userLocation

                updateUserProfileStepLocation()
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

    private fun updateUserProfileStepLocation() {
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
                location = CommonConstants.USER?.location,
                companyServices = null,
                rating = 0f,
                fileName = CommonConstants.USER?.fileName.toString()
            )

            Log.d("RegistrationStep4Location", user.toString())
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