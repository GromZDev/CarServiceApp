package carService.app.ui.registration

import android.location.Address
import android.location.Geocoder
import android.text.TextUtils
import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.data.model.Location
import carService.app.databinding.RegistrationStep4LocationFragmentBinding
import carService.app.utils.SharedPreferencesHelper
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import java.util.*


@KoinApiExtension
class RegistrationStep4LocationFragment(
    override val layoutId: Int = R.layout.registration_step4_location_fragment
) :
    BaseFragment<RegistrationStep4LocationFragmentBinding>() {

    companion object {
        const val TAG = "RegistrationStep4LocationFragment"
        fun newInstance() = RegistrationStep4LocationFragment()
    }

    private val viewModel by viewModel<RegistrationStep4LocationViewModel>()
    private val prefs by inject<SharedPreferencesHelper>()
    private var userLocation: Location? = null

    override fun initViews() {
        super.initViews()

        hideToolbarAndBottomNav()

        binding.myLocationTextview.visibility = View.GONE
        binding.successAccountButton.isEnabled = false
        binding.switchButton.setOnCheckedChangeListener { _, visibility ->
            if (visibility) {
                binding.successAccountButton.isEnabled = true
            } else if (!visibility) {
                binding.successAccountButton.isEnabled = false
            }

        }
        binding.successAccountButton.setOnClickListener {
            updateProfile()
            navigate(R.id.registrationStep5RoleFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep3ConfirmPhotoFragment)
        }

        binding.getLocationButton.setOnClickListener {
            navigate(R.id.registrationStep4Map)
        }

        val a = arguments?.getString("Location by Search")
        if (a != null) {
            binding.myLocationInfo.text = a
            binding.switchButton.visibility = View.GONE
            binding.takeLaterTextview.visibility = View.GONE
            binding.successAccountButton.isEnabled = true
        }

    }

    override fun initViewModel() {
        super.initViewModel()

        doInScope {
            viewModel.newUser.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    binding.myLocationTextview.visibility = View.VISIBLE
                    prefs.isRegistrationStep3 = true
                    navigate(R.id.registrationStep5RoleFragment)
                } else if (it == null && userLocation?.latitude?.isNotEmpty() == true &&
                    userLocation?.longitude?.isNotEmpty() == true
                ) {
                    navigate(R.id.registrationStep5RoleFragment)
//                    view?.showsnackBar(getString(R.string.access_failed))
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
                if (isStateException != "" && userLocation?.latitude?.isNotEmpty() == true &&
                    userLocation?.longitude?.isNotEmpty() == true
                ) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun updateProfile() {

        userLocation = if (binding.myLocationInfo.text.isEmpty()) {
            Location(null, null)
        } else {
            val loc = binding.myLocationInfo.text

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocationName(loc as String?, 1)
            val address: Address = addresses[0]
            val longitude: Double = address.longitude
            val latitude: Double = address.latitude
            Location(latitude.toString(), longitude.toString())
        }

        when {
            TextUtils.isEmpty(userLocation.toString()) -> {
                view?.showsnackBar(getString(R.string.not_empty_email_password))
            }

            else -> {
                viewModel.updateProfileUser(userLocation ?: Location("0.000", "0.000"))
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
        }
    }
}


