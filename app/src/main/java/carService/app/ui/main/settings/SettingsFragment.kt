package carService.app.ui.main.settings

import androidx.fragment.app.Fragment
import carService.app.R

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel


}