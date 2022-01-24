package carService.app.ui.auth

import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.ForgotPasswordFragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class ForgotPasswordFragment(override val layoutId: Int = R.layout.forgot_password_fragment) :
    BaseFragment<ForgotPasswordFragmentBinding>() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    //  private val vm by viewModel<ForgotPasswordViewModel>()

    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()

//        binding.nextCreateAccountButton.setOnClickListener {
//          //  navigate(R.id.forgotPasswordStep2Fragment)
//        }
        binding.resetViaSmsButton.visibility = View.GONE

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.loginFragment)
        }

        binding.textShareHint.setOnClickListener {
            navigate(R.id.forgotPasswordStep3Passwords)
        }

        binding.resetViaEmailButton.setOnClickListener {
            navigate(R.id.forgotPasswordStepEnterEmail)
        }


    }

//    override fun initViewModel() {
//        doInScope {
//            vm.isSendPasswordResetEmail.collect { isSendPasswordResetEmail ->
//                if (isSendPasswordResetEmail) {
//                   view?.showsnackBar(getString(R.string.approve_sms))
//                    navigate(R.id.forgotPasswordStepEnterEmail)
//                }
//            }
//            vm.isStateException.collect { isStateException ->
//                if (isStateException !="") {
//                    view?.showsnackBar(getString(R.string.failed_send_sms))
//                }
//            }
//        }
//        doInScopeResume {
//            vm.isStateException.collect { isStateException ->
//                if (isStateException !="") {
//                    view?.showsnackBar(getString(R.string.failed_send_sms))
//                }
//            }
//        }
//    }
}