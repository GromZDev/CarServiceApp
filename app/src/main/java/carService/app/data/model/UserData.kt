package carService.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var uid: String = "",
    var name: String = "",
    var lastName: String = "",
    var nickName: String = "",
    var email: String = "",
    var phone: String = "",
    val lang: String = "",
    val type: TYPE? = null,
    var profileImageUrl: String? = "",
    var location: Location? = null,
    var companyServices: OrganisationServices? = null,
    var rating: Float? = 0f
) : Parcelable {
    enum class TYPE {
        PERSONAL,
        ORGANISATION
    }
}

fun getAllOrganisations() = mutableListOf(
    UserData(
        "111",
        "CSF Workshop",
        "CSF",
        "CSF",
        "CSF@carsound-factory.ru",
        "+7 (995)782-68-50",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "http://www.carsound-factory.ru/images/intec.png",
        Location("55.852236", "37.389728"),
        OrganisationServices(service1 = "Установка автозвука", service2 = "Шумоизоляция"),
        9.3f
    ),
    UserData(
        "112",
        "CarCustom",
        "Каркастом",
        "Каркастом",
        "info@carcustom.ru",
        "+7 (917) 561-18-21",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "https://carcustom.ru/wp-content/uploads/2019/12/carcustom-logo2.png",
        Location("55.852022", "37.389449"),
        OrganisationServices(service1 = "Автозвук", service2 = "Полировка"),
        8.5f
    ),
    UserData(
        "113",
        "ZF-EXPERT",
        "ZF",
        "Зет Эф Эксперт",
        "info@zf-expert.ru",
        "8 (495) 723-88-66",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "http://zf-expert.ru/sites/default/files/Logo.png",
        Location("55.860857", "37.579138"),
        OrganisationServices(service1 = "Детейлинг", service2 = "Кузовной ремонт"),
        7.2f
    ),
    UserData(
        "114",
        "АГС",
        "АГС",
        "АГС",
        "support@agscenter.ru",
        "+7 (499) 110-61-68",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "https://www.agscenter.ru/template/img/logo.png",
        Location("55.78740336", "37.50765033"),
        OrganisationServices(service1 = "Ремонт авто", service2 = "Генераторы"),
        8.6f
    ),
    UserData(
        "115",
        "BY Tuning",
        "Tuning",
        "BY Tuning",
        "info@by-tuning.ru",
        "+7 (903) 589-02-50",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "https://by-tuning.ru/images/logo-new4.png",
        Location("55.791396", "37.618509"),
        OrganisationServices(service1 = "Тюнинг выхлопа", service2 = "Турбины"),
        9.0f
    ),
    UserData(
        "116",
        "Колесный центр",
        "Шина",
        "Шиншина",
        "89154963000@mail.ru",
        "+7 (915) 496-3000",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "https://shina-vsem.ru/i/header_logo.png",
        Location("56.068259", "37.390471"),
        OrganisationServices(service1 = "Шиномонтаж", service2 = "Перебортовка"),
        8.8f
    ),
    UserData(
        "117",
        "CHECK",
        "Двигатель",
        "Запчасти",
        "info@check.com.ru",
        "+7 (927) 223-7123",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "http://check64.ru/images/logo.png?crc=4165143253",
        Location("51.542931", "46.029391"),
        OrganisationServices(service1 = "Запчасти", service2 = "Масла и жидкости"),
        7.1f
    ),
    UserData(
        "118",
        "Time",
        "Мойка",
        "Моя",
        "2866006@mail.ru",
        "+7 (343) 286-60-06",
        "RUS",
        UserData.TYPE.ORGANISATION,
        "https://gryazi96.net/bitrix/templates/carwash_responsive/img/logo.png",
        Location("56.844628", "60.632425"),
        OrganisationServices(service1 = "Автомойка", service2 = "Полировка фар"),
        7.7f
    )
)