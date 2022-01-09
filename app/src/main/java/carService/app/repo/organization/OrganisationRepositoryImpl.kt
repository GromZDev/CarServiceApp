package carService.app.repo.organization

import carService.app.data.model.UserData
import carService.app.data.model.getAllOrganisations
import carService.app.data.model.organization.OrganisationData
import carService.app.data.model.organization.OrganisationServiceList
import carService.app.data.model.organization.getFullDataOfAllOrganisations
import carService.app.data.model.organization.getFullDataOfAllServices

class OrganisationRepositoryImpl: OrganisationRepository {

    override fun getAllOrganisationMockData(): List<UserData> = getAllOrganisations()
    override fun getAllOrganisationFullMockData(): List<OrganisationData> = getFullDataOfAllOrganisations()
    override fun getAllOrganisationServiceMockData(): List<OrganisationServiceList> = getFullDataOfAllServices()

}