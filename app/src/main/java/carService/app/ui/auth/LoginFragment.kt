package carService.app.ui.auth

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import carService.app.base.BaseFragment
import carService.app.R
import carService.app.databinding.FragmentLoginBinding
import carService.app.di.FireBaseModule
import carService.app.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import timber.log.Timber

@KoinApiExtension
class LoginFragment(override val layoutId: Int = R.layout.fragment_login) :
    BaseFragment<FragmentLoginBinding>() {
    companion object {
        private const val TAG = "LoginFragment"
        fun newInstance() = LoginFragment()
        const val GOOGLE_SIGN_IN = 123
    }

    private val vm by viewModel<LoginViewModel>()
    private val fbase by inject<FireBaseModule>()
    private val prefs by inject<SharedPreferencesHelper>()

    var email: String = ""
    var password: String = ""
    override fun initViews() {

        hideToolbarAndBottomNav()

        binding.forgotTextview.setOnClickListener {
            navigate(R.id.forgotPasswordFragment)
        }

        binding.googleLoginButton.setOnClickListener {
            signIn()
        }

        binding.createAccountButton.setOnClickListener {
            navigate(R.id.registrationFragment)
        }

        binding.loginButton.setOnClickListener {
            email = binding.emailTextInputField.text.toString().trim()
            password = binding.passwordTextInputFieldInput.text.toString().trim()
            when {
                !binding.emailTextInputField.validateEmail(email) -> {
                    view?.showsnackBar(getString(R.string.valid_email))
                }
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) -> {
                    view?.showsnackBar(getString(R.string.not_empty_email_password))
                }
                password.length < 8 -> {
                    view?.showsnackBar(getString(R.string.valid_password))
                }
                else -> {
                    vm.loginByEmail(email, password)
                    binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.checkAuth()
    }

    override fun initViewModel() {
        doInScope {
            vm.stateInitial.collect {stateInitial->
                if (stateInitial) {
                    checkProfile()
                }
            }
            vm.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    checkProfile()
//                    stepRegistration()
                }
                else if (!isLoggedIn && email.isNotEmpty() && password.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_internet))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }

        doInScopeResume {
            vm.stateInitial.collect {stateInitial->
                if (stateInitial) {
                    checkProfile()
                }
            }
            vm.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    checkProfile()
                }
            }
            vm.isStateException.collect { isStateException ->
                if (isStateException !="" && !prefs.isAuthed && email.isNotEmpty() && password.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_internet))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun checkProfile() {
        CommonConstants.USER?.let {
            if (it.name != "" && it.lastName != "" && it.phone != "") {
                prefs.isRegistrationStep1 = true
                if (it.profileImageUrl != "") {
                    prefs.isRegistrationStep2 = true
                    if (it.location != null) {
                        prefs.isRegistrationStep3 = true
                        if (it.type != null) {
                            prefs.isRegistrationStep4 = true
                        }
                    }
                }
            }
        }
        if (CommonConstants.USER?.uid != "")  stepRegistration()
    }

    private fun signIn() {
        val signInIntent: Intent = fbase.provideGoogleClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (GOOGLE_SIGN_IN == requestCode) {
            googleSignIn(data)
        }
    }

    private fun googleSignIn(data: Intent?) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
            Timber.d("firebaseAuthWithGoogle:%s", account.id)
            vm.loginByGoogle(account)
            if (prefs.isInitial)  stepRegistration()
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }

        binding.forgotTextview.setOnClickListener {
            navigate(R.id.forgotPasswordFragment)
        }
    }

    private fun stepRegistration() {
        when {
            !prefs.isRegistrationStep1 -> navigate(R.id.registrationStep2Fragment)
            !prefs.isRegistrationStep2 -> navigate(R.id.registrationStep3Fragment)
            !prefs.isRegistrationStep3 -> navigate(R.id.registrationStep4LocationFragment)
            !prefs.isRegistrationStep4 -> navigate(R.id.registrationStep5RoleFragment)
            else -> navigate(R.id.mainUserFragment)
        }
    }
}