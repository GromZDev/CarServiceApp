package carService.app.ui.auth

import android.text.TextUtils
import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.ForgotPasswordStep3FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import carService.app.utils.validateEmail
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ForgotPasswordStep3Fragment(override val layoutId: Int = R.layout.forgot_password_step3_fragment) :
    BaseFragment<ForgotPasswordStep3FragmentBinding>() {

    companion object {
        fun newInstance() = ForgotPasswordStep3Fragment()
    }

    private val vm by viewModel<ForgotPasswordViewModel>()

    var passwordOne: String = ""
    var passwordTwo: String = ""

    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()

        binding.nextResetPasswordButton.setOnClickListener {
            navigate(R.id.forgotPasswordStep4SuccessFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.forgotPasswordFragment)
        }

        binding.approveResetPasswordButton.setOnClickListener {
            passwordOne = binding.newPasswordInputFieldOne.text.toString().trim()
            passwordTwo = binding.newPasswordInputFieldTwo.text.toString().trim()
            when {

                TextUtils.isEmpty(passwordOne) || TextUtils.isEmpty(passwordOne) -> {
                    view?.showsnackBar(getString(R.string.not_empty_email_password))
                }

                passwordOne.length != passwordTwo.length -> {
                    view?.showsnackBar(getString(R.string.failed_confirm_password))
                }

                passwordOne.length < 8 || passwordTwo.length < 8 -> {
                    view?.showsnackBar(getString(R.string.valid_password))
                }
                else -> {
                    vm.updatePassword(passwordOne)
                    binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun initViewModel() {
        doInScope {
            vm.isUpdatePassword.collect { isUpdatePassword ->
                if (isUpdatePassword) {
                    view?.showsnackBar(getString(R.string.approve_password))
                    navigate(R.id.forgotPasswordStep4SuccessFragment)
                }
            }
            vm.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.failed_update_password))
                }
            }
        }
        doInScopeResume {
            vm.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.failed_update_password))
                }
            }
        }
    }
}