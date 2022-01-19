package carService.app.ui.auth

import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.ForgotPasswordEnterEmailFragmentBinding
import carService.app.utils.CommonConstants.USER
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class ForgotPasswordStep2EnterEmailFragment(override val layoutId: Int = R.layout.forgot_password_enter_email_fragment) :
    BaseFragment<ForgotPasswordEnterEmailFragmentBinding>() {

    companion object {
        fun newInstance() = ForgotPasswordStep2EnterEmailFragment()
    }

    private val vm by viewModel<ForgotPasswordViewModel>()

    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()

        binding.nextButton.setOnClickListener {
            navigate(R.id.forgotPasswordStep4SuccessFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.loginFragment)
        }

        binding.doResetPasswordButton.setOnClickListener {
            vm.sendPasswordResetEmail(binding.emailInputFieldOne.text.toString().trim())
              USER?.let { it1 -> vm.sendPasswordResetEmail(it1.email) }
            navigate(R.id.forgotPasswordStep3Passwords)
        }
    }

    override fun initViewModel() {
        doInScope {
            vm.isSendPasswordResetEmail.collect { isSendPasswordResetEmail ->
                if (isSendPasswordResetEmail) {
                    view?.showsnackBar(getString(R.string.approve_by_email))
                    navigate(R.id.forgotPasswordStep3Passwords)
                }
            }
            vm.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.failed_send_sms))
                }
            }
        }
        doInScopeResume {
            vm.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.failed_send_sms))
                }
            }
        }
    }
}