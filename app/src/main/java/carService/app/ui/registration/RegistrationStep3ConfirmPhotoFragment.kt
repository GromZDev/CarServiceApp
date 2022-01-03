package carService.app.ui.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationStep2FragmentBinding
import carService.app.databinding.RegistrationStep3ConfirmPhotoFragmentBinding
import carService.app.databinding.RegistrationStep3FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class RegistrationStep3ConfirmPhotoFragment : Fragment(R.layout.registration_step3_confirm_photo_fragment) {

    companion object {
        const val TAG = "RegistrationStep3ConfirmPhotoFragment"
        fun newInstance() = RegistrationStep3ConfirmPhotoFragment()
    }

    private val binding: RegistrationStep3ConfirmPhotoFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.nextCreateAccountButton.setOnClickListener {
            navigate(R.id.registrationStep4LocationFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep3Fragment)
        }
    }

}