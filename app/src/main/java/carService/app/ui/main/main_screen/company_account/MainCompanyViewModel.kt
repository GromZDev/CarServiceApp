package carService.app.ui.main.main_screen.company_account

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import carService.app.base.BaseViewModel
import carService.app.repo.organization.OrganisationRepository
import carService.app.repo.organization.OrganisationRepositoryImpl
import carService.app.ui.main.main_screen.personal_account.CompaniesNearAppState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainCompanyViewModel(
    app: Application,
    private val liveDataToObserve: MutableLiveData<CompaniesNearAppState> =
        MutableLiveData(),
    private val organisationRepositoryImpl: OrganisationRepository = OrganisationRepositoryImpl()
) : BaseViewModel(app) {

    fun getLiveData() = liveDataToObserve

    fun getOrganisationData() = getDataFromDB()

    private fun getDataFromDB() {
        modelScope.launch {
            liveDataToObserve.value = CompaniesNearAppState.Loading
            liveDataToObserve.postValue(
                CompaniesNearAppState.Success(
                    organisationRepositoryImpl.getAllOrganisationData(),
                )
            )
        }
    }
}