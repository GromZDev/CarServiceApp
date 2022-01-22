package carService.app.data.model.personal

import java.util.*
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PersonalServicesRequests(
    val theme: String? = "",
    val overview: String? = "",
    val price: Int? = 0,
    val data: String? = ""

): Parcelable
