package carService.app.ui.main.main_screen.personal_account.companyDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import carService.app.repo.organization.OrganisationRepository
import carService.app.repo.organization.OrganisationRepositoryImpl

class CompanyDetailsViewModel(
    private val liveDataToObserve: MutableLiveData<CompanyDetailsAppState> =
        MutableLiveData(),
    private val organisationRepositoryImpl: OrganisationRepository = OrganisationRepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getFullOrganisationMockData() = getFullDataFromLocalSource()

    private fun getFullDataFromLocalSource() {
        liveDataToObserve.value = CompanyDetailsAppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(
                CompanyDetailsAppState.Success(
                    organisationRepositoryImpl.getAllOrganisationFullMockData(),
                    organisationRepositoryImpl.getAllOrganisationServiceMockData()
                )
            )
        }.start()
    }
}