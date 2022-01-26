package carService.app.ui.registration

import android.app.Application
import android.util.Log
import carService.app.base.BaseViewModel
import carService.app.data.model.UserData
import carService.app.utils.CommonConstants
import carService.app.utils.FirebaseConstants.Companion.ORGANIZATION_ACCOUNT
import carService.app.utils.FirebaseConstants.Companion.PERSONAL_ACCOUNT
import carService.app.utils.FirebaseConstants.Companion.USERS
import carService.app.utils.SharedPreferencesHelper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationStep5RoleViewModel(
    app: Application,
    private val fireStore: FirebaseFirestore,
    private val prefs: SharedPreferencesHelper,
) : BaseViewModel(app) {
    val newUser: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val typeAccount : MutableStateFlow<String> = MutableStateFlow("")

    val isStateException = MutableStateFlow("")

    fun updateProfileUser(userRole: UserData.TYPE) {
        modelScope.launch {
            try {
                CommonConstants.USER?.type = userRole

                updateUserProfileRole()
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

    private fun updateUserProfileRole() {
        modelScope.launch {
            val user = UserData(
                uid = CommonConstants.USER?.uid.toString(),
                name = CommonConstants.USER?.name.toString(),
                lastName = CommonConstants.USER?.lastName.toString(),
                nickName = CommonConstants.USER?.nickName.toString(),
                email = CommonConstants.USER?.email.toString(),
                phone = CommonConstants.USER?.phone.toString(),
                lang = CommonConstants.USER?.lang.toString(),
                type = CommonConstants.USER?.type,
                profileImageUrl = CommonConstants.USER?.profileImageUrl.toString(),
                location = CommonConstants.USER?.location,
                companyServices = null,
                rating = 0f
            )

            Log.d("RegistrationStep5Role", user.toString())
            updateProfile(user)
            createNodeAccount(user)
        }
    }

    private fun updateProfile(user: UserData){
        modelScope.launch {
            val collection = fireStore.collection(USERS)
            val document = collection.document(user.uid)
            document
                .set(user)
                .addOnSuccessListener { newUser.value = user }
                .addOnFailureListener { newUser.value = null }
                .await()
        }
    }

    private fun createNodeAccount(user: UserData){
        modelScope.launch {
          var account: String = ""
            if (user.type == UserData.TYPE.PERSONAL) account = PERSONAL_ACCOUNT
            else if (user.type == UserData.TYPE.ORGANISATION)  account = ORGANIZATION_ACCOUNT
            typeAccount.value =account
            prefs.typeAccount = typeAccount.value
            val collection = fireStore.collection(account)
            val document = collection.document(user.uid)
            document
                .set(user)
                .addOnSuccessListener { newUser.value = user }
                .addOnFailureListener { newUser.value = null }
                .await()
        }
    }
}