package carService.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganisationServices(
    var service1: String = "",
    var service2: String = "",
    var service3: String = "",
    var service4: String = "",
    var service5: String = "",
    var service6: String = "",
    val service7: String = ""
): Parcelable