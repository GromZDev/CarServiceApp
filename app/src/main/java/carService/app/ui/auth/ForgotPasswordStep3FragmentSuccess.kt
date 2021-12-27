package carService.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.ForgotPasswordStep3SuccessBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class ForgotPasswordStep3FragmentSuccess : Fragment(R.layout.forgot_password_step3_success) {

    companion object {
        fun newInstance() = ForgotPasswordStep3FragmentSuccess()
    }

    private val binding: ForgotPasswordStep3SuccessBinding by viewBinding()
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.goToLoginButton.setOnClickListener {
            navigate(R.id.loginFragment)
        }

    }
}