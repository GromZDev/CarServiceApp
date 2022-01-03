package carService.app.repo.organization

import carService.app.data.model.UserData
import carService.app.data.model.getAllOrganisations

class OrganisationRepositoryImpl: OrganisationRepository {

    override fun getAllOrganisationMockData(): List<UserData> = getAllOrganisations()

}