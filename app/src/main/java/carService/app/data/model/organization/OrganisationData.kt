package carService.app.data.model.organization

import android.os.Parcelable
import carService.app.data.model.Location
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class OrganisationData(
    val uid: String? = "",
    var name: String? = "",
    var lastName: String? = "",
    var nickName: String? = "",
    var telephoneNumbers: List<OrganisationTelNumbers>? = null,
    var email: String = "",
    var overview: String? = "",
    var allServices: MutableList<OrganisationServiceList>? = null,
    var mainOrganisationPhoto: String? = "",
    var otherOrganisationPhotos: List<OtherOrganisationPhotos>? = null,
    var location: Location? = null,
    var social: @RawValue OrganisationSocial? = null,
    var rating: Float? = null,
    var workingTime: @RawValue WorkingTime? = null,

    ) : Parcelable


fun getFullDataOfAllOrganisations() = mutableListOf(
    OrganisationData(
        "1", "CSF Workshop", "", "CSF",
        getOrganisationsTelNumbers(),
        "csf@carsound-factory.ru", "Описание компании CSF Workshop: шумоизоляция," +
                "установка автозвука, детейлинг и дополнительное оборудование",
        allServices = getFullDataOfAllServices(),
        "https://yt3.ggpht.com/HAxU8S0WfWgYZldAFsGbIY1OuSvusFQg5_aZg2NijZw6I-sioKEJ6ZpNmkKWxsGvk9vSWot1=s176-c-k-c0x00ffffff-no-rj",
        location = Location("55.852236", "37.389728"), social = getAllOrganisationSocial(),
        rating = 9.2f, workingTime = getWorkingTime()
    )
)