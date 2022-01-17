package carService.app.ui.auth

import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.ForgotPasswordStep2FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ForgotPasswordStep2Fragment(override val layoutId: Int = R.layout.forgot_password_step2_fragment) :
    BaseFragment<ForgotPasswordStep2FragmentBinding>() {

    companion object {
        fun newInstance() = ForgotPasswordStep2Fragment()
    }

    private val vm by viewModel<ForgotPasswordViewModel>()

    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()

        binding.nextResetPasswordButton.setOnClickListener {
            navigate(R.id.forgotPasswordStep3SuccessFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.forgotPasswordFragment)
        }
    }

    override fun initViewModel() {
        doInScope {
            vm.isUpdatePassword.collect { isUpdatePassword ->
                if (isUpdatePassword) {
                    view?.showsnackBar(getString(R.string.approve_password))
                    navigate(R.id.forgotPasswordStep2Fragment)
                }
            }
            vm.isStateException.collect { isStateException ->
                if (isStateException !="") {
                    view?.showsnackBar(getString(R.string.failed_update_password))
                }
            }
        }
        doInScopeResume {
            vm.isStateException.collect { isStateException ->
                if (isStateException !="") {
                    view?.showsnackBar(getString(R.string.failed_update_password))
                }
            }
        }
    }
}