package carService.app.ui.registration

import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationSuccessBinding
import carService.app.utils.FirebaseConstants.Companion.ORGANIZATION_ACCOUNT
import carService.app.utils.FirebaseConstants.Companion.PERSONAL_ACCOUNT
import carService.app.utils.SharedPreferencesHelper
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationStepSuccessFragment(
    override val layoutId: Int = R.layout.registration_success
)  :
    BaseFragment<RegistrationSuccessBinding>() {

    companion object {
        const val TAG = "RegistrationStepSuccessFragment"
        fun newInstance() = RegistrationStepSuccessFragment()
    }
    private val vm by viewModel<RegistrationStep5RoleViewModel>()
    private val prefs by inject<SharedPreferencesHelper>()

    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()
        binding.goToAccountButton.setOnClickListener {
            goToAccount()
        }
    }

    private fun goToAccount() {
                when(prefs.typeAccount) {
                    PERSONAL_ACCOUNT -> navigate(R.id.mainUserFragment)
                    ORGANIZATION_ACCOUNT -> navigate(R.id.mainUserFragment)
                    // возможно другое действие
                    else -> navigate(R.id.mainUserFragment)
                }

    }
}