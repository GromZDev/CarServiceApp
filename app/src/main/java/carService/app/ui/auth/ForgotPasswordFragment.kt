package carService.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.ForgotPasswordFragmentBinding
import carService.app.databinding.RegistrationStep3FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class ForgotPasswordFragment : Fragment(R.layout.forgot_password_fragment) {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private val binding: ForgotPasswordFragmentBinding by viewBinding()
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.nextCreateAccountButton.setOnClickListener {
            navigate(R.id.forgotPasswordStep2Fragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.loginFragment)
        }
    }
}