package carService.app.ui.main.menu_screens.company_menu.announcements

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import carService.app.base.BaseViewModel
import carService.app.data.model.organization.announcements.Announcement
import carService.app.data.model.organization.announcements.OrganisationAnnouncements
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinApiExtension
import java.text.SimpleDateFormat
import java.util.*

@KoinApiExtension
class CompanyAnnouncementViewModel(
    app: Application,
    private var fireStore: FirebaseFirestore
) : BaseViewModel(app) {
    val newService: MutableStateFlow<List<OrganisationAnnouncements>?> = MutableStateFlow(null)
    val serviceCompanyAnnouncement: MutableStateFlow<List<OrganisationAnnouncements>?> =
        MutableStateFlow(null)
    val isStateException = MutableStateFlow("")
    val isStateException2 = MutableStateFlow("")

    @SuppressLint("SimpleDateFormat")
    fun updateOrganisationServiceList(
        serviceTheme: String,
        serviceOverview: String,
        servicePrice: String,
        uid: String,
        imageUri: Uri,
        adapter: CompanyAnnouncementsAdapter
    ) {
        modelScope.launch {
            try {
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormat.format(calendar.time)

                val request = OrganisationAnnouncements(
                    uid = uid,
                    id = 10,
                    serviceName = serviceTheme,
                    servicePhoto = imageUri.toString(),
                    price = servicePrice.toInt(),
                    serviceOverview = serviceOverview,
                    data = date
                )
              //  newService.value = listOf(request)

             //   CommonConstants.ORG?.orgAnnouncements = listOf(request)

                updateOrgServiceToDB(adapter, request)
                delay(1000)
            } catch (exception: TimeoutCancellationException) {
                isStateException.value = "1 - " + exception.message
                newService.value = null

            } catch (exception: Exception) {
                isStateException.value = "2 - " + exception.message
                newService.value = null
            }


            if (uid.isNotEmpty()) {
                val formatter = SimpleDateFormat("dd_MM_yyy_HH_mm_ss", Locale.getDefault())
                val now = Date()
                val fileName = formatter.format(now)
                val storageReference =
                    FirebaseStorage.getInstance().getReference(
                        "images/organisation/organisationAnnouncement/$uid/$fileName")

                imageUri.let {
                    storageReference.putFile(it).addOnSuccessListener {

                    }.addOnFailureListener {
                    }
                }
            }
        }
    }

    private fun updateOrgServiceToDB(
        adapter: CompanyAnnouncementsAdapter,
        data: OrganisationAnnouncements
    ) {
        modelScope.launch {
            val user = OrganisationAnnouncements(
                data.uid,
                data.id,
                data.serviceName,
                data.servicePhoto,
                data.price,
                data.serviceOverview,
                data.data
            )


            val collection = fireStore.collection("organisationAnnouncement")
            val document = collection.document(user.uid)
            document
                .update("orgAnnouncements", FieldValue.arrayUnion(user))
                .addOnSuccessListener {
                    newService.value = listOf(user)
                    user.let { it1 -> adapter.appendItem(it1) }
                }
                .addOnFailureListener { newService.value = null }
                .await()
        }
    }

    fun gerAnnouncementsData(uid: String, adapter: CompanyAnnouncementsAdapter) {
        modelScope.launch {
            try {
                if (uid.isNotEmpty()) {
                    eventChangeListener(uid, adapter)
                    delay(1000)
                }
            } catch (exception: TimeoutCancellationException) {
                isStateException2.value = "1 - " + exception.message
                serviceCompanyAnnouncement.value = null

            } catch (exception: Exception) {
                isStateException2.value = "2 - " + exception.message
                serviceCompanyAnnouncement.value = null
            }
        }
    }

    private fun eventChangeListener(currentUser: String, adapter: CompanyAnnouncementsAdapter) {
        modelScope.launch {
            fireStore = FirebaseFirestore.getInstance()
            fireStore.collection("organisationAnnouncement").document(currentUser)
                .get()
                .addOnSuccessListener { link ->
                    val announceList = link.toObject<Announcement>()
                    if (announceList?.orgAnnouncements !== null) {
                        serviceCompanyAnnouncement.value = announceList.orgAnnouncements
                        adapter.setAllServices(announceList.orgAnnouncements as MutableList<OrganisationAnnouncements>)
                    }

                }
                .addOnFailureListener {
                    serviceCompanyAnnouncement.value = null
                }
                .await()
        }
    }

}