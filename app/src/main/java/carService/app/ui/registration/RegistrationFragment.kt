package carService.app.ui.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationFragmentBinding
import carService.app.ui.auth.LoginFragment
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationFragment : Fragment(R.layout.registration_fragment) {

    companion object {
        const val TAG = "RegistrationFragment"
        fun newInstance() = RegistrationFragment()
    }

    private val binding: RegistrationFragmentBinding by viewBinding()
    private lateinit var viewModel: RegistrationViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.haveAnAccountTextview.setOnClickListener {
            goToLoginFragment()
        }

        binding.createAccountButton.setOnClickListener {
            goToRegistrationStep2Fragment()
        }

    }


    private fun goToLoginFragment() {
        val manager = activity?.supportFragmentManager
        manager?.let {
            manager.beginTransaction()
                .replace(
                    R.id.nav_main_fragment,
                    LoginFragment.newInstance()
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun goToRegistrationStep2Fragment() {
        val manager = activity?.supportFragmentManager
        manager?.let {
            manager.beginTransaction()
                .replace(
                    R.id.nav_main_fragment,
                    RegistrationStep2Fragment.newInstance()
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

}