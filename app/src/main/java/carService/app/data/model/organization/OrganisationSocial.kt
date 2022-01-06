package carService.app.data.model.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrganisationSocial(
    var socialInstagram: SocialTYPE? = null,
    var socialInstagramPath: String? = null,
    var socialFacebook: SocialTYPE? = null,
    var socialFacebookPath: String? = null,
    var socialVk: SocialTYPE? = null,
    var socialVkPath: String? = null,
    var socialYoutube: SocialTYPE? = null,
    var socialYoutubePath: String? = null,
): Parcelable {

    enum class SocialTYPE {
        INSTAGRAM,
        FACEBOOK,
        VK,
        YOUTUBE
    }
}


fun getAllOrganisationSocial() =
    OrganisationSocial(socialInstagram = OrganisationSocial.SocialTYPE.INSTAGRAM,
        socialInstagramPath = "https://www.instagram.com/csfworkshop/",
    socialYoutube = OrganisationSocial.SocialTYPE.YOUTUBE,
        socialYoutubePath = "https://www.youtube.com/channel/UCb9meEW1Zd26AFWGeMOiCoA")


