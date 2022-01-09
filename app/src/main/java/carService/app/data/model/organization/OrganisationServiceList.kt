package carService.app.data.model.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganisationServiceList(
    var serviceName: String? = null,
    var serviceMainPhoto: String? = "",
    var serviceOtherPhotoList: List<OtherServicePhoto>? = null,
    var price: Int? = 0,
    var serviceOverview: String? = "",
): Parcelable

fun getFullDataOfAllServices() = mutableListOf(
    OrganisationServiceList("Шумоизоляция", "http://www.carsound-factory.ru/upload/iblock/097/0971dddb7964f0d94531c3e3a8ec53d1.jpg",
        price = 36000, serviceOverview = "Полная шумоизоляция салона автомобиля по адекватным ценам"
    ),
    OrganisationServiceList("Установка автозвука", "http://www.carsound-factory.ru/upload/iblock/bd1/IMG_6156.jpg",
    price = 10000, serviceOverview = "Установка автозвука в Москве: любой уровень аудиосистемы и проектные работы")
)