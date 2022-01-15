package carService.app.ui.registration

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationStep4LocationFragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class RegistrationStep4LocationFragment : Fragment(R.layout.registration_step4_location_fragment) {

    companion object {
        const val TAG = "RegistrationStep4LocationFragment"
        fun newInstance() = RegistrationStep4LocationFragment()
    }

    private val binding: RegistrationStep4LocationFragmentBinding by viewBinding()
    private lateinit var viewModel: RegistrationStep4LocationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.successAccountButton.setOnClickListener {
            navigate(R.id.registrationStep5RoleFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep3ConfirmPhotoFragment)
        }

        binding.getLocationButton.setOnClickListener {
            navigate(R.id.registrationStep4Map)
        }

        val a = arguments?.getString("Location by Search")
        binding.myLocationInfo.text = a
    }

}