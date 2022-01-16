package carService.app.ui.registration

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.RegistrationStep3ConfirmPhotoFragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showToast
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class RegistrationStep3ConfirmPhotoFragment :
    Fragment(R.layout.registration_step3_confirm_photo_fragment) {

    companion object {
        const val TAG = "RegistrationStep3ConfirmPhotoFragment"
        const val BUNDLE_EXTRA = "MY_Photo"
        fun newInstance(bundle: Bundle?): RegistrationStep3ConfirmPhotoFragment {
            val fragment = RegistrationStep3ConfirmPhotoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val a = arguments?.getParcelable<Uri>(BUNDLE_EXTRA)
    //  private lateinit var myUri: Uri

    private val binding: RegistrationStep3ConfirmPhotoFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbarAndBottomNav()

        if (arguments !== null) {
            val a = arguments?.getParcelable<Uri>(BUNDLE_EXTRA)
            val myUri: Uri = a as Uri
            binding.youImageIw.setImageURI(myUri)
        }

        binding.nextCreateAccountButton.setOnClickListener {
            if (arguments !== null) {
                val b = arguments?.getParcelable<Uri>(BUNDLE_EXTRA)
                val myUri: Uri = b as Uri
                uploadImageToDatabase(myUri)
            }
            navigate(R.id.registrationStep4LocationFragment)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep3Fragment)
        }

    }

    private fun uploadImageToDatabase(imageUri: Uri) {

        val formatter = SimpleDateFormat("dd_MM_yyy_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageUri).addOnSuccessListener {
            showToast("Successfully uploaded!")

        }.addOnFailureListener {
            showToast("Error has occurred! Sorry...")
        }


    }

}