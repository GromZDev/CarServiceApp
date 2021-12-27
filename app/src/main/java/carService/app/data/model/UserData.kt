package carService.app.data.model

data class UserData(
    var uid: String = "",
    var name: String = "",
    var lastName: String = "",
    var nickName: String = "",
    var email: String = "",
    var phone: String = "",
    val lang: String = "",
    val type: TYPE? = null,
    var profileImageUrl: String = "",
    var location: Location? = null,
) {
    enum class TYPE {
        PERSONAL,
        ORGANISATION
    }
}