package carService.app.repo.organization

import carService.app.data.model.UserData

interface OrganisationRepository {

    fun getAllOrganisationMockData(): List<UserData>
}