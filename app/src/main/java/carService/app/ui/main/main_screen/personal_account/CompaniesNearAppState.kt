package carService.app.ui.main.main_screen.personal_account

import carService.app.data.model.organization.OrganisationData

sealed class CompaniesNearAppState {
    data class Success(val organisationData: List<OrganisationData>) : CompaniesNearAppState()
    data class Error(val error: Throwable) : CompaniesNearAppState()
    object Loading : CompaniesNearAppState()

}