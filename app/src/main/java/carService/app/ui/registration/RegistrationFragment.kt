package carService.app.ui.registration

import android.text.TextUtils
import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationFragmentBinding
import carService.app.utils.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
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
    private val prefs by inject<SharedPreferencesHelper>()

    var name: String = ""
    var email: String = ""
    var password: String = ""

    override fun initViews() {

        binding.haveAnAccountTextview.setOnClickListener {
            navigate(R.id.loginFragment)
        }

        binding.createAccountButton.setOnClickListener {
            register()
        }
    }

    fun register() {
        name = binding.nickNameInputField.text.toString().trim()
        email = binding.eMailTextInputField.text.toString().trim()
        password = binding.passwordInputFieldReg.text.toString().trim()
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
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun initViewModel() {
        doInScope {
            vm.newUser.collect {
                if (it != null) {
//                    prefs.isFirstOpen = false
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    navigate(R.id.registrationStep2Fragment)
                }
                else if (it == null && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_internet))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            vm.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.failed_new_user))
                }
            }
        }

        doInScopeResume {
            vm.isStateException.collect { isStateException ->
                if (isStateException !="" && !prefs.isAuthed && email.isNotEmpty() && password.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_internet))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
    }
}