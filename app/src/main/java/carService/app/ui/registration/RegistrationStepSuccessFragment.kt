package carService.app.ui.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationSuccessBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class RegistrationStepSuccessFragment : Fragment(R.layout.registration_success) {

    companion object {
        const val TAG = "RegistrationStepSuccessFragment"
        fun newInstance() = RegistrationStepSuccessFragment()
    }

    private val binding: RegistrationSuccessBinding by viewBinding()
    private lateinit var viewModel: RegistrationStepSuccessViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        binding.goToAccountButton.setOnClickListener {
            navigate(R.id.mainUserFragment)
        }
    }

}