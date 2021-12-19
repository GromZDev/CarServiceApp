package carService.app.ui.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationStep5RoleFragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class RegistrationStep5RoleFragment : Fragment(R.layout.registration_step5_role_fragment) {

    companion object {
        const val TAG = "RegistrationStep5RoleFragment"
        fun newInstance() = RegistrationStep5RoleFragment()
    }

    private val binding: RegistrationStep5RoleFragmentBinding by viewBinding()
    private lateinit var viewModel: RegistrationStep5RoleViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.nextToSuccessAccountButton.setOnClickListener {
            navigate(R.id.registrationIsSuccessFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep4LocationFragment)
        }

        /** Временно для наглядности: если выбираем аккаунт компании,
         * то после регистрации нужно попасть сюда */
        binding.companyAccountButton.setOnClickListener {
            navigate(R.id.mainCompanyPageFragment)
        }
    }

}