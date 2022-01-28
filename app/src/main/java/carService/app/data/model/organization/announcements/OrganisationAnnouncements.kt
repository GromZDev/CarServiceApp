package carService.app.data.model.organization.announcements

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganisationAnnouncements(
    var uid: String = "",
    var id: Long? = null,
    var serviceName: String? = null,
    var servicePhoto: String? = "",
    var price: Int? = 0,
    var serviceOverview: String? = "",
    val data: String? = "",
    val fileName: String? = ""
): Parcelable
