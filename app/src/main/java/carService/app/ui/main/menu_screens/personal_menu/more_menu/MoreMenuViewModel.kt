package carService.app.ui.main.menu_screens.personal_menu.more_menu

import android.app.Application
import carService.app.base.BaseViewModel
import carService.app.utils.FirebaseAuthHelper
import carService.app.utils.SharedPreferencesHelper

class MoreMenuViewModel(
    app: Application,
    private val prefs: SharedPreferencesHelper
) : BaseViewModel(app) {

    fun logout() {
        FirebaseAuthHelper.instance.logout()
        prefs.isAuthed = false
    }
}