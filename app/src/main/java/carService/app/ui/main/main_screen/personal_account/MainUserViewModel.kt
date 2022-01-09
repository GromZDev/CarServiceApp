package carService.app.ui.main.main_screen.personal_account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import carService.app.repo.organization.OrganisationRepository
import carService.app.repo.organization.OrganisationRepositoryImpl

class MainUserViewModel(
    private val liveDataToObserve: MutableLiveData<CompaniesNearAppState> =
        MutableLiveData(),
    private val organisationRepositoryImpl: OrganisationRepository = OrganisationRepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getOrganisationMockData() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = CompaniesNearAppState.Loading
        Thread {
            Thread.sleep(1500)
            liveDataToObserve.postValue(
                CompaniesNearAppState.Success(
                    organisationRepositoryImpl.getAllOrganisationMockData()
                )
            )
        }.start()
    }

}