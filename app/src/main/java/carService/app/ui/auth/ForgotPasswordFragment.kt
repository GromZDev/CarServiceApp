package carService.app.ui.auth

import androidx.fragment.app.Fragment
import carService.app.R

class ForgotPasswordFragment : Fragment(R.layout.forgot_password_fragment) {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private lateinit var viewModel: ForgotPasswordViewModel
}