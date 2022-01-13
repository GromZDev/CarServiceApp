package carService.app.ui.registration

import android.text.TextUtils
import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationStep2FragmentBinding
import carService.app.utils.SharedPreferencesHelper
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

@KoinApiExtension
class RegistrationStep2Fragment(
    override val layoutId: Int = R.layout.registration_step2_fragment
) : BaseFragment<RegistrationStep2FragmentBinding>() {

    companion object {
        const val TAG = "RegistrationStep2Fragment"
        fun newInstance() = RegistrationStep2Fragment()
    }

    private val vm by viewModel<RegistrationStep2ViewModel>()
    private val prefs by inject<SharedPreferencesHelper>()

    var name: String = ""
    var lastName: String = ""
    var phone: String = ""

    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()

        binding.createAccountButton.setOnClickListener {
            updateProfile()
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationFragment)
        }
        initMask()
    }

    override fun initViewModel() {
        super.initViewModel()
        doInScope {
            vm.newUser.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    prefs.isRegistrationStep1 = true
                    navigate(R.id.registrationStep3Fragment)
                } else if (it == null && name.isNotEmpty() && lastName.isNotEmpty() && phone.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            vm.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.access_failed))
                }
            }
        }
        doInScopeResume {
            vm.isStateException.collect { isStateException ->
                if (isStateException != "" && name.isNotEmpty() && lastName.isNotEmpty() && phone.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun initMask() {
        val mask: MaskImpl = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.passwordInputFieldReg)
    }

    fun updateProfile() {
        name = binding.nickNameInputField.text.toString().trim()
        lastName = binding.eMailTextInputField.text.toString().trim()
        phone = binding.passwordInputFieldReg.text.toString().trim()
        when {
            TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(name) -> {
                view?.showsnackBar(getString(R.string.not_empty_email_password))
            }
            phone.length < 8 -> {
                view?.showsnackBar(getString(R.string.valid_phone))
            }
            else -> {
                vm.updateProfileUser(name, lastName, phone)
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
        }
    }
}