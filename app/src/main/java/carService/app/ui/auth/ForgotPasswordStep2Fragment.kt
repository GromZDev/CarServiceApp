package carService.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.ForgotPasswordStep2FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class ForgotPasswordStep2Fragment : Fragment(R.layout.forgot_password_step2_fragment) {

    companion object {
        fun newInstance() = ForgotPasswordStep2Fragment()
    }

    private val binding: ForgotPasswordStep2FragmentBinding by viewBinding()
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.nextResetPasswordButton.setOnClickListener {
            navigate(R.id.forgotPasswordStep3SuccessFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.forgotPasswordFragment)
        }
    }
}