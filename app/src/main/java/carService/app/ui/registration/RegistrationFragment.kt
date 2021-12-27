package carService.app.ui.registration

import android.text.TextUtils
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationFragmentBinding
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import carService.app.utils.validateEmail
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationFragment(override val layoutId: Int = R.layout.registration_fragment) :
    BaseFragment<RegistrationFragmentBinding>() {

    companion object {
        const val TAG = "RegistrationFragment"
        fun newInstance() = RegistrationFragment()
    }
    private val vm by viewModel<RegistrationViewModel>()

    override fun initViews() {

        binding.haveAnAccountTextview.setOnClickListener {
            navigate(R.id.loginFragment)
        }

        binding.createAccountButton.setOnClickListener {
            register()
        }
    }

    fun register() {
        val name = binding.nickNameInputField.text.toString().trim()
        val email = binding.eMailTextInputField.text.toString().trim()
        val password = binding.passwordInputFieldReg.text.toString().trim()
        when {
            !binding.eMailTextInputField.validateEmail(email) -> {
                view?.showsnackBar(getString(R.string.valid_email))
            }
            TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) -> {
                view?.showsnackBar(getString(R.string.not_empty_email_password))
            }
            password.length < 8 -> {
                view?.showsnackBar(getString(R.string.valid_password))
            }
//            vm.newUser.value == null -> {
//                view?.showsnackBar("Ошибка. Аккаунт не создан!")
//            }
            else -> {
                vm.registerByEmail(name,email,password)
            }
        }
    }

    override fun initViewModel() {
        doInScope {
            vm.newUser.collect {
                if (it != null) {
                    navigate(R.id.registrationStep2Fragment)
                }
//                else view?.showsnackBar(getString(R.string.access_internet))
            }
        }
    }
}