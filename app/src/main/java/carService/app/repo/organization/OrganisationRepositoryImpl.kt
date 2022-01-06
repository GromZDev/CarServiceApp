package carService.app.repo.organization

import carService.app.data.model.UserData
import carService.app.data.model.getAllOrganisations
import carService.app.data.model.organization.OrganisationData
import carService.app.data.model.organization.getFullDataOfAllOrganisations

class OrganisationRepositoryImpl: OrganisationRepository {

    override fun getAllOrganisationMockData(): List<UserData> = getAllOrganisations()
    override fun getAllOrganisationFullMockData(): List<OrganisationData> = getFullDataOfAllOrganisations()

}