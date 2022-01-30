package carService.app.repo.organization

import carService.app.data.model.UserData
import carService.app.data.model.organization.OrganisationData
import carService.app.data.model.organization.OrganisationServiceList
import carService.app.data.model.organization.announcements.Announcement
import carService.app.data.model.organization.announcements.OrganisationAnnouncements

interface OrganisationRepository {

    fun getAllOrganisationData(): List<OrganisationData>

    fun getAllOrganisationFullMockData(): List<OrganisationData>

    fun getAllOrganisationServiceMockData(): List<OrganisationServiceList>
}