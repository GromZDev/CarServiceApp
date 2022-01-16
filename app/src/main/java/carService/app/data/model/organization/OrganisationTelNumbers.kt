package carService.app.data.model.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganisationTelNumbers(
    val tel: String? = null,
) : Parcelable

fun getOrganisationsTelNumbers() = mutableListOf(
    OrganisationTelNumbers("+7 999 669 31 44"),
    OrganisationTelNumbers("+7 495 589 22 33")
)
