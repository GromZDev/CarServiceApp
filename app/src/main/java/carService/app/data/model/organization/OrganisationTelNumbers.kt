package carService.app.data.model.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganisationTelNumbers(
    val tel1: String? = null,
    val tel2: String? = null,
    val tel3: String? = null,
    val tel4: String? = null,
    val tel5: String? = null
): Parcelable
