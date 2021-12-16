package carService.app.ui.registration

import androidx.fragment.app.Fragment
import carService.app.R

class RegistrationFragment : Fragment(R.layout.registration_fragment) {

    companion object {
        const val TAG = "RegistrationFragment"
        fun newInstance() = RegistrationFragment()
    }

    private lateinit var viewModel: RegistrationViewModel

}