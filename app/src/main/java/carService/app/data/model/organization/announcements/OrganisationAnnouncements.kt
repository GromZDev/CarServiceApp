package carService.app.data.model.organization.announcements

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganisationAnnouncements(
    var uid: String = "",
    var id: Int? = null,
    var serviceName: String? = null,
    var servicePhoto: String? = "",
    var price: Int? = 0,
    var serviceOverview: String? = "",
    val data: String? = ""
): Parcelable