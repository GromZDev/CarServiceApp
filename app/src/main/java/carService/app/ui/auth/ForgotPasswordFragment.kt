package carService.app.ui.auth

import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.ForgotPasswordFragmentBinding
import carService.app.utils.CommonConstants.USER
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ForgotPasswordFragment(override val layoutId: Int = R.layout.forgot_password_fragment) :
    BaseFragment<ForgotPasswordFragmentBinding>() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    private val vm by viewModel<ForgotPasswordViewModel>()

    @KoinApiExtension
    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()

        binding.nextCreateAccountButton.setOnClickListener {
            navigate(R.id.forgotPasswordStep2Fragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.loginFragment)
        }

        binding.textShareHint.setOnClickListener {

        }

        binding.resetPasswordEmailHint.setOnClickListener {
            USER?.let { it1 -> vm.sendPasswordResetEmail(it1.email) }
        }
    }

    override fun initViewModel() {
        doInScope {
            vm.isSendPasswordResetEmail.collect { isSendPasswordResetEmail ->
                if (isSendPasswordResetEmail) {
                   view?.showsnackBar(getString(R.string.approve_sms))
                    navigate(R.id.forgotPasswordStep2Fragment)
                }
            }
            vm.isStateException.collect { isStateException ->
                if (isStateException !="") {
                    view?.showsnackBar(getString(R.string.failed_send_sms))
                }
            }
        }
        doInScopeResume {
            vm.isStateException.collect { isStateException ->
                if (isStateException !="") {
                    view?.showsnackBar(getString(R.string.failed_send_sms))
                }
            }
        }
    }
}