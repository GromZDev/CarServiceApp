package carService.app.ui.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationStep2FragmentBinding
import carService.app.databinding.RegistrationStep3FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class RegistrationStep3Fragment : Fragment(R.layout.registration_step3_fragment) {

    companion object {
        const val TAG = "RegistrationStep3Fragment"
        fun newInstance() = RegistrationStep3Fragment()
    }

    private val binding: RegistrationStep3FragmentBinding by viewBinding()
    private lateinit var viewModel: RegistrationStep3ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.nextCreateAccountButton.setOnClickListener {
            navigate(R.id.registrationStep4LocationFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep2Fragment)
        }
    }

}