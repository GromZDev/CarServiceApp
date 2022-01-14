package carService.app.ui.registration

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationStep3FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate

class RegistrationStep3Fragment(
    override val layoutId: Int = R.layout.registration_step3_fragment
) :
    BaseFragment<RegistrationStep3FragmentBinding>() {

    companion object {
        const val TAG = "RegistrationStep3Fragment"
        fun newInstance() = RegistrationStep3Fragment()
    }

    private lateinit var viewModel: RegistrationStep3ViewModel

    private lateinit var imageUri: Uri

    override fun initViews() {
        super.initViews()

        hideToolbarAndBottomNav()

        binding.nextCreateAccountButton.setOnClickListener {
            navigate(R.id.registrationStep3ConfirmPhotoFragment, null)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep2Fragment)
        }

        binding.galleryPhotoButton.setOnClickListener {
            selectPhotoFromStorage()
        }
    }

    private fun selectPhotoFromStorage() {
        doInScope {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!

            passImageToNextFragment(imageUri)
        }
    }

    private fun passImageToNextFragment(imageUri: Uri) {
        val bundle = Bundle()
        bundle.putParcelable(RegistrationStep3ConfirmPhotoFragment.BUNDLE_EXTRA, imageUri)
        navigate(R.id.registrationStep3ConfirmPhotoFragment, bundle)
    }
}