package carService.app.ui.registration

import androidx.fragment.app.Fragment
import carService.app.R

class RegistrationStep2Fragment : Fragment(R.layout.registration_step2_fragment) {

    companion object {
        const val TAG = "RegistrationStep2Fragment"
        fun newInstance() = RegistrationStep2Fragment()
    }

    private lateinit var viewModel: RegistrationStep2ViewModel

}