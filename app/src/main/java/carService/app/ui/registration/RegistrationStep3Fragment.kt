package carService.app.ui.registration

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationStep3FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import java.io.ByteArrayOutputStream

private const val REQUEST_CODE_FOR_CAMERA = 156
private const val REQUEST_CODE_FOR_STORAGE = 100

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

        binding.takeAPhotoButton.setOnClickListener {
            makePhoto()
        }
    }

    private fun selectPhotoFromStorage() {
        doInScope {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, REQUEST_CODE_FOR_STORAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FOR_STORAGE && resultCode == RESULT_OK) {
            imageUri = data?.data!!

            passImageToNextFragment(imageUri)
        }

        if (resultCode != RESULT_CANCELED) {
            if (requestCode == REQUEST_CODE_FOR_CAMERA && resultCode == RESULT_OK && data != null) {
                val image: Bitmap = data.extras?.get("data") as Bitmap
                imageUri = context?.let { getImageUri(it, image) }!!

                passImageToNextFragment(imageUri)
            }
        }

    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    private fun passImageToNextFragment(imageUri: Uri) {
        val bundle = Bundle()
        bundle.putParcelable(RegistrationStep3ConfirmPhotoFragment.BUNDLE_EXTRA, imageUri)
        navigate(R.id.registrationStep3ConfirmPhotoFragment, bundle)
    }

    private fun makePhoto() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.CAMERA
                )
            } ==
            PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_CODE_FOR_CAMERA
            )

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE_FOR_CAMERA)

    }

}