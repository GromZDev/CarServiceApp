package carService.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
        var latitude: String? = null,
        var longitude: String? = null,
): Parcelable
