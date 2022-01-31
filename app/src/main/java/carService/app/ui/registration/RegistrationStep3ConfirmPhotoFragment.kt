package carService.app.ui.registration

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationStep3ConfirmPhotoFragmentBinding
import carService.app.utils.SharedPreferencesHelper
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RegistrationStep3ConfirmPhotoFragment(override val layoutId: Int = R.layout.registration_step3_confirm_photo_fragment) :
    BaseFragment<RegistrationStep3ConfirmPhotoFragmentBinding>() {

    companion object {
        const val TAG = "RegistrationStep3ConfirmPhotoFragment"
        const val BUNDLE_EXTRA = "MY_Photo"
        fun newInstance(bundle: Bundle?): RegistrationStep3ConfirmPhotoFragment {
            val fragment = RegistrationStep3ConfirmPhotoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel by viewModel<RegistrationStep3ConfirmPhotoViewModel>()
    private val prefs by inject<SharedPreferencesHelper>()

    private var userImage: String = ""

    override fun initViews() {
        super.initViews()

        hideToolbarAndBottomNav()

        if (arguments !== null) {
            val a = arguments?.getParcelable<Uri>(BUNDLE_EXTRA)
            val myUri: Uri = a as Uri
            binding.youImageIw.setImageURI(myUri)
        }

        binding.nextCreateAccountButton.setOnClickListener {
            updateProfile()
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep3Fragment)
        }

        binding.closeIw.setOnClickListener {
            navigate(R.id.registrationStep3Fragment)
        }
    }

    override fun initViewModel() {
        super.initViewModel()

        doInScope {
            viewModel.newUser.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    prefs.isRegistrationStep2 = true
                    navigate(R.id.registrationStep4LocationFragment)
                } else if (it == null && userImage.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    view?.showsnackBar(getString(R.string.access_failed))
                }
            }
        }
        doInScopeResume {
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "" && userImage.isNotEmpty()) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }

    }

    private fun updateProfile() {
        val c = arguments?.getParcelable<Uri>(BUNDLE_EXTRA)
        val myUri: Uri = c as Uri
        userImage = myUri.toString()

        when {
            TextUtils.isEmpty(userImage) -> {
                view?.showsnackBar(getString(R.string.not_empty_email_password))
            }

            else -> {
                viewModel.updateProfileUser(userImage, myUri)
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
        }
    }
}