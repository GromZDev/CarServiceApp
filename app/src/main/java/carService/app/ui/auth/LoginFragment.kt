package carService.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.FragmentLoginBinding
import carService.app.ui.main.main_screen.MainUserFragment
import carService.app.ui.registration.RegistrationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
class LoginFragment : Fragment(R.layout.fragment_login), KoinComponent {

    companion object {
        private const val TAG ="LoginFragment"
        fun newInstance() = LoginFragment()
    }

    private val binding: FragmentLoginBinding by viewBinding()
    private val vm by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Скрываем навигацию там, где она не нужна */
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.GONE

        binding.forgotTextview.setOnClickListener {
            goToForgotPasswordFragment()
        }

        binding.createAccountButton.setOnClickListener {
            goToRegistrationFragment()
        }

        binding.loginButton.setOnClickListener {
            goToMainUserFragment()
        }
    }

    private fun goToForgotPasswordFragment() {
        val manager = activity?.supportFragmentManager
        manager?.let {
            manager.beginTransaction()
                .replace(
                    R.id.nav_main_fragment,
                    ForgotPasswordFragment.newInstance()
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun goToRegistrationFragment() {
        val manager = activity?.supportFragmentManager
        manager?.let {
            manager.beginTransaction()
                .replace(
                    R.id.nav_main_fragment,
                    RegistrationFragment.newInstance()
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun goToMainUserFragment() {
        val manager = activity?.supportFragmentManager
        manager?.let {
            manager.beginTransaction()
                .replace(
                    R.id.nav_main_fragment,
                    MainUserFragment.newInstance()
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}