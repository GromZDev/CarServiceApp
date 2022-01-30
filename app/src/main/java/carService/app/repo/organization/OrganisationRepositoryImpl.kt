package carService.app.repo.organization

import android.util.Log
import carService.app.data.model.organization.OrganisationData
import carService.app.data.model.organization.OrganisationServiceList
import carService.app.data.model.organization.getFullDataOfAllOrganisations
import carService.app.data.model.organization.getFullDataOfAllServices
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class OrganisationRepositoryImpl : OrganisationRepository {

    //   override fun getAllOrganisationData(): List<UserData> = getAllOrganisations()
    override fun getAllOrganisationData(): List<OrganisationData> = getAllOrganisation()

    override fun getAllOrganisationFullMockData(): List<OrganisationData> =
        getFullDataOfAllOrganisations()

    override fun getAllOrganisationServiceMockData(): List<OrganisationServiceList> =
        getFullDataOfAllServices()


    private fun getAllOrganisation(): ArrayList<OrganisationData> {
        val list = arrayListOf<OrganisationData>()
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("organisationAccount")
            .addSnapshotListener(object :
                com.google.firebase.firestore.EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            list.add(dc.document.toObject(OrganisationData::class.java))
                        }
                    }
                }
            })
        return list
    }
}