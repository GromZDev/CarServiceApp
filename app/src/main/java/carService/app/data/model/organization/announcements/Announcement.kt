package carService.app.data.model.organization.announcements

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Announcement(
    var orgAnnouncements: List<OrganisationAnnouncements>? = null

): Parcelable