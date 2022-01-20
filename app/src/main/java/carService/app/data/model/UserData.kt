package carService.app.data.model

import android.os.Parcelable
import carService.app.data.model.personal.PersonalServicesRequests
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var uid: String = "",
    var name: String = "",
    var lastName: String = "",
    var nickName: String = "",
    var email: String = "",
    var phone: String? = "",
    val lang: String = "",
    var type: TYPE? = null,
    var profileImageUrl: String? = "",
    var location: Location? = null,
    var companyServices: OrganisationServices? = null,
    var personalServices: List<PersonalServicesRequests>? = null,
    //= mutableListOf(PersonalServicesRequests("", "", 0, Date(1111))),
    var rating: Float? = 0f
) : Parcelable {
    enum class TYPE {
        PERSONAL,
        ORGANISATION
    }
}

fun getAllOrganisations() = mutableListOf(
    UserData(
        uid = "111",
        name = "CSF Workshop",
        lastName = "CSF",
        nickName = "CSF",
        email = "CSF@carsound-factory.ru",
        phone = "+7 (995)782-68-50",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "http://www.carsound-factory.ru/images/intec.png",
        location = Location("55.852236", "37.389728"),
        companyServices = OrganisationServices(
            service1 = "Установка автозвука",
            service2 = "Шумоизоляция"
        ),
        rating = 9.3f,
        personalServices = null
    ),
    UserData(
        uid = "112",
        name = "CarCustom",
        lastName = "Каркастом",
        nickName = "Каркастом",
        email = "info@carcustom.ru",
        phone = "+7 (917) 561-18-21",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "https://carcustom.ru/wp-content/uploads/2019/12/carcustom-logo2.png",
        location = Location("55.852022", "37.389449"),
        companyServices = OrganisationServices(service1 = "Автозвук", service2 = "Полировка"),
        rating = 8.5f
    ),
    UserData(
        uid = "113",
        name = "ZF-EXPERT",
        lastName = "ZF",
        nickName = "Зет Эф Эксперт",
        email = "info@zf-expert.ru",
        phone = "8 (495) 723-88-66",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "http://zf-expert.ru/sites/default/files/Logo.png",
        location = Location("55.860857", "37.579138"),
        companyServices = OrganisationServices(
            service1 = "Детейлинг",
            service2 = "Кузовной ремонт"
        ),
        rating = 7.2f
    ),
    UserData(
        uid = "114",
        name = "АГС",
        lastName = "АГС",
        nickName = "АГС",
        email = "support@agscenter.ru",
        phone = "+7 (499) 110-61-68",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "https://www.agscenter.ru/template/img/logo.png",
        location = Location("55.78740336", "37.50765033"),
        companyServices = OrganisationServices(service1 = "Ремонт авто", service2 = "Генераторы"),
        rating = 8.6f
    ),
    UserData(
        uid = "115",
        name = "BY Tuning",
        lastName = "Tuning",
        nickName = "BY Tuning",
        email = "info@by-tuning.ru",
        phone = "+7 (903) 589-02-50",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "https://by-tuning.ru/images/logo-new4.png",
        location = Location("55.791396", "37.618509"),
        companyServices = OrganisationServices(service1 = "Тюнинг выхлопа", service2 = "Турбины"),
        rating = 9.0f
    ),
    UserData(
        uid = "116",
        name = "Колесный центр",
        lastName = "Шина",
        nickName = "Шиншина",
        email = "89154963000@mail.ru",
        phone = "+7 (915) 496-3000",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "https://shina-vsem.ru/i/header_logo.png",
        location = Location("56.068259", "37.390471"),
        companyServices = OrganisationServices(service1 = "Шиномонтаж", service2 = "Перебортовка"),
        rating = 8.8f
    ),
    UserData(
        uid = "117",
        name = "CHECK",
        lastName = "Двигатель",
        nickName = "Запчасти",
        email = "info@check.com.ru",
        phone = "+7 (927) 223-7123",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "http://check64.ru/images/logo.png?crc=4165143253",
        location = Location("51.542931", "46.029391"),
        companyServices = OrganisationServices(
            service1 = "Запчасти",
            service2 = "Масла и жидкости"
        ),
        rating = 7.1f
    ),
    UserData(
        uid = "118",
        name = "Time",
        lastName = "Мойка",
        nickName = "Моя",
        email = "2866006@mail.ru",
        phone = "+7 (343) 286-60-06",
        lang = "RUS",
        UserData.TYPE.ORGANISATION,
        profileImageUrl = "https://gryazi96.net/bitrix/templates/carwash_responsive/img/logo.png",
        location = Location("56.844628", "60.632425"),
        companyServices = OrganisationServices(service1 = "Автомойка", service2 = "Полировка фар"),
        rating = 7.7f
    )
)