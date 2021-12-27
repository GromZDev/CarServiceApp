package carService.app.ui.main.menu_screens.company_menu.more_company_menu

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.SharedPreferencesHelper

class MoreCompanyMenuViewModel(
    app: Application,
    private val prefs: SharedPreferencesHelper
) : BaseViewModel(app) {

    fun logout() {
        FirebaseAuthHelper.instance.logout()
        prefs.isAuthed = false
    }
}