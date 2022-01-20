package carService.app.ui.main.menu_screens.company_menu.more_company_menu

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.utils.CommonConstants.USER
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.SharedPreferencesHelper
import org.koin.core.component.inject

class MoreCompanyMenuViewModel(
    app: Application,
    private val prefs: SharedPreferencesHelper,
) : BaseViewModel(app) {
    fun logout() {
        FirebaseAuthHelper.instance.logout()
        prefs.isAuthed = false
        prefs.isFirstOpen = true
        prefs.isRegistrationStep1 = false
        prefs.isRegistrationStep2 = false
        prefs.isRegistrationStep3 = false
        prefs.isRegistrationStep4 = false
        prefs.isInitial = false
    }


}