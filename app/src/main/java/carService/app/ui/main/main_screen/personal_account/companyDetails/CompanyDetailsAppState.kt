package carService.app.ui.main.main_screen.personal_account.companyDetails

import carService.app.data.model.organization.OrganisationData

sealed class CompanyDetailsAppState {
    data class Success(val organisationData: List<OrganisationData>) : CompanyDetailsAppState()
    data class Error(val error: Throwable) : CompanyDetailsAppState()
    object Loading : CompanyDetailsAppState()

}