package carService.app.ui.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationFragmentBinding
import carService.app.databinding.RegistrationStep2FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class RegistrationStep2Fragment : Fragment(R.layout.registration_step2_fragment) {

    companion object {
        const val TAG = "RegistrationStep2Fragment"
        fun newInstance() = RegistrationStep2Fragment()
    }

    private val binding: RegistrationStep2FragmentBinding by viewBinding()
    private lateinit var viewModel: RegistrationStep2ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.createAccountButton.setOnClickListener {
            navigate(R.id.registrationStep3Fragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationFragment)
        }
    }

}