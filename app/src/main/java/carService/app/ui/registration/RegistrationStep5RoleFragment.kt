package carService.app.ui.registration

import android.text.TextUtils
import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.data.model.UserData
import carService.app.databinding.RegistrationStep5RoleFragmentBinding
import carService.app.utils.SharedPreferencesHelper
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationStep5RoleFragment(
    override val layoutId: Int = R.layout.registration_step5_role_fragment
) :
    BaseFragment<RegistrationStep5RoleFragmentBinding>() {

    companion object {
        const val TAG = "RegistrationStep5RoleFragment"
        fun newInstance() = RegistrationStep5RoleFragment()
    }

    private val viewModel by viewModel<RegistrationStep5RoleViewModel>()
    private val prefs by inject<SharedPreferencesHelper>()
    private var userType: UserData.TYPE? = null

    private var buttonPersonalIsSelected: Boolean = false
    private var buttonCompanyIsSelected: Boolean = false

    override fun initViews() {
        super.initViews()

        hideToolbarAndBottomNav()
        binding.nextToSuccessAccountButton.isEnabled = false

        if (binding.personalAccountButton.isSelected) {
            binding.nextToSuccessAccountButton.isEnabled = true
        } else if (binding.companyAccountButton.isSelected) {
            binding.nextToSuccessAccountButton.isEnabled = true
        }

        binding.nextToSuccessAccountButton.setOnClickListener {
            if (binding.personalAccountButton.isSelected && binding.companyAccountButton.isSelected ) {
                showMessage("Необходимо выбрать только один вариант!")
                return@setOnClickListener
            }
           when {
               binding.personalAccountButton.isSelected ->{
                   userType = UserData.TYPE.PERSONAL
                   updateProfile()
               }
               binding.companyAccountButton.isSelected ->{
                   userType = UserData.TYPE.ORGANISATION
                   updateProfile()
               }
               else -> navigate(R.id.action_registrationStep5Role_to_successFragment)
           }
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep4LocationFragment)
        }

        binding.companyAccountButton.setOnClickListener {
            binding.companyAccountButton.isSelected = !binding.companyAccountButton.isSelected
            if (binding.companyAccountButton.isSelected) {
                buttonCompanyIsSelected = true
                binding.nextToSuccessAccountButton.isEnabled = true
            }
            buttonCompanyIsSelected = false
        }

        binding.personalAccountButton.setOnClickListener {
            binding.personalAccountButton.isSelected = !binding.personalAccountButton.isSelected
            if (binding.personalAccountButton.isSelected) {
                buttonPersonalIsSelected = true
                binding.nextToSuccessAccountButton.isEnabled = true
            }
            buttonPersonalIsSelected = false
        }
    }

    override fun initViewModel() {
        super.initViewModel()

        doInScope {
            viewModel.newUser.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    prefs.isRegistrationStep4 = true
                    navigate(R.id.action_registrationStep5Role_to_successFragment)
                } else if (it == null && userType?.name?.isNotEmpty() == true
                ) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.access_failed))
                }
            }
        }
        doInScopeResume {
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "" && userType?.name?.isNotEmpty() == true
                ) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun updateProfile() {

        if (binding.personalAccountButton.isSelected) {
            userType = UserData.TYPE.PERSONAL
        }
        if (binding.companyAccountButton.isSelected) {
            userType = UserData.TYPE.ORGANISATION
        }

        when {
            TextUtils.isEmpty(userType?.name) -> {
                view?.showsnackBar(getString(R.string.not_empty_email_password))
            }

            else -> {
                userType?.let { viewModel.updateProfileUser(it) }
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
        }
    }
}