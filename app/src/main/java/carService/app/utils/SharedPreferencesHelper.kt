package carService.app.utils

import android.content.Context

private const val APP_PREFERENCES = "prefs"

class SharedPreferencesHelper(ctx: Context) {
    private val prefs = ctx.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        private const val FIRST_OPEN_KEY = "FIRST_OPEN"
        private const val CURRENT_USER_ID = "USER_ID"
        private const val IS_AUTHED_KEY = "IS_AUTHED"
        private const val IS_INITIAL = "IS_INITIAL"
        private const val IS_REGISTRATION_STEP_1 = "IS_REGISTRATION_STEP_1"
        private const val IS_REGISTRATION_STEP_2 = "IS_REGISTRATION_STEP_2"
        private const val IS_REGISTRATION_STEP_3 = "IS_REGISTRATION_STEP_3"
        private const val IS_REGISTRATION_STEP_4 = "IS_REGISTRATION_STEP_4"
    }

    var userId: String? = null
        set(value) {
            field = value
            prefs.edit().putString(CURRENT_USER_ID, value).apply()
        }
        get() = prefs.getString(CURRENT_USER_ID, null)

    var isFirstOpen = true
        set(value) {
            field = value
            prefs.edit().putBoolean(FIRST_OPEN_KEY, value).apply()
        }
        get() = prefs.getBoolean(FIRST_OPEN_KEY, true)

    var isAuthed = false
        set(value) {
            field = value
            prefs.edit().putBoolean(IS_AUTHED_KEY, value).apply()
        }
        get() = prefs.getBoolean(IS_AUTHED_KEY, false)

    var isInitial = false
        set(value) {
            field = value
            prefs.edit().putBoolean(IS_INITIAL, value).apply()
        }
        get() = prefs.getBoolean(IS_INITIAL, false)

    var isRegistrationStep1 = false
        set(value) {
            field = value
            prefs.edit().putBoolean(IS_REGISTRATION_STEP_1, value).apply()
        }
        get() = prefs.getBoolean(IS_REGISTRATION_STEP_1, false)

    var isRegistrationStep2 = false
        set(value) {
            field = value
            prefs.edit().putBoolean(IS_REGISTRATION_STEP_2, value).apply()
        }
        get() = prefs.getBoolean(IS_REGISTRATION_STEP_2, false)

    var isRegistrationStep3 = false
        set(value) {
            field = value
            prefs.edit().putBoolean(IS_REGISTRATION_STEP_3, value).apply()
        }
        get() = prefs.getBoolean(IS_REGISTRATION_STEP_3, false)

    var isRegistrationStep4 = false
        set(value) {
            field = value
            prefs.edit().putBoolean(IS_REGISTRATION_STEP_4, value).apply()
        }
        get() = prefs.getBoolean(IS_REGISTRATION_STEP_4, false)
}