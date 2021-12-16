package carService.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.FragmentLoginBinding
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
        binding.headerTextview.text = getString(R.string.simple_text)
    }
}