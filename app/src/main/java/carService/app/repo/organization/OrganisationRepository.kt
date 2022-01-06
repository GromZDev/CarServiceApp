package carService.app.repo.organization

import carService.app.data.model.UserData
import carService.app.data.model.organization.OrganisationData

interface OrganisationRepository {

    fun getAllOrganisationMockData(): List<UserData>

    fun getAllOrganisationFullMockData(): List<OrganisationData>
}