package carService.app.ui.registration

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RegistrationStep3FragmentBinding
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import carService.app.utils.showAlertDialogPermission
import carService.app.utils.showToast
import java.io.ByteArrayOutputStream

private const val PERMISSION_STORAGE = 189
private const val PERMISSION_WRITE_STORAGE = 246
private const val PERMISSION_CAMERA = 345

class RegistrationStep3Fragment(
    override val layoutId: Int = R.layout.registration_step3_fragment
) :
    BaseFragment<RegistrationStep3FragmentBinding>() {

    companion object {
        const val TAG = "RegistrationStep3Fragment"
        fun newInstance() = RegistrationStep3Fragment()
    }

    // private lateinit var viewModel: RegistrationStep3ViewModel

    private var imageUri: Uri? = null

    private val getActionPhotoFromStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { pic ->
            imageUri = pic?.data?.data
            imageUri?.let { it -> passImageToNextFragment(it) }
        }

    private val getActionMakePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { pic ->
            val image: Bitmap = pic?.data?.extras?.get("data") as Bitmap
            imageUri = context?.let { img -> getImageUri(img, image) }!!

            imageUri?.let { t1 -> passImageToNextFragment(t1) }
        }

    override fun initViews() {
        super.initViews()

        hideToolbarAndBottomNav()

        binding.nextCreateAccountButton.setOnClickListener {
//            navigate(R.id.registrationStep3ConfirmPhotoFragment, null)
            navigate(R.id.registrationStep4LocationFragment, null)
        }

        binding.backButtonImage.setOnClickListener {
            navigate(R.id.registrationStep2Fragment)
        }

        binding.galleryPhotoButton.setOnClickListener {
            checkForPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                "Storage",
                PERMISSION_STORAGE
            )
        }

        binding.takeAPhotoButton.setOnClickListener {
            checkForPermissions(android.Manifest.permission.CAMERA, "Camera", PERMISSION_CAMERA)
        }
    }

    private fun selectPhotoFromStorage() {
        doInScope {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            getActionPhotoFromStorage.launch(intent)
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
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            getActionMakePhoto.launch(cameraIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        when {
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    permission
                )
            } == PackageManager.PERMISSION_GRANTED -> {
                showToast("Доступ разрешен!")
                if (requestCode == PERMISSION_STORAGE) {
                    selectPhotoFromStorage()
                }
                if (requestCode == PERMISSION_CAMERA) {
                    checkForPermissions(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        "Write",
                        PERMISSION_WRITE_STORAGE
                    )
                }
                if (requestCode == PERMISSION_WRITE_STORAGE) {
                    makePhoto()
                }
            }
            shouldShowRequestPermissionRationale(permission) -> showDialog(
                permission,
                name,
                requestCode
            )
            else -> activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(permission), requestCode
                )
            }
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {

        showAlertDialogPermission(
            permission,
            name,
            requestCode,
            "Необходимо разрешение $name для доступа к фото",
            "Для приложения нужно разрешение"
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showToast("Permission Refused $name")
            } else {
                showToast("Permission Granted $name")
            }
        }

        when (requestCode) {
            PERMISSION_CAMERA -> innerCheck("Camera")
            PERMISSION_STORAGE -> innerCheck("Storage")
            PERMISSION_WRITE_STORAGE -> innerCheck("Write")
        }
    }

}