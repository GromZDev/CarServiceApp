package carService.app.data.model.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtherServicePhoto(
    val photo: String? = null,
): Parcelable