package carService.app.ui.main.menu_screens.personal_menu.profile

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.data.model.UserData
import carService.app.databinding.ProfileFragmentBinding
import carService.app.utils.AppImageView
import carService.app.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import org.koin.core.component.KoinApiExtension
import java.util.*

@KoinApiExtension
class ProfileFragment(override val layoutId: Int = R.layout.profile_fragment) :
    BaseFragment<ProfileFragmentBinding>() {

    companion object {
        const val TAG = "ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid.toString()
        if (currentUser.isNotEmpty()) {
            getData(currentUser)
        }
    }

    private fun getData(uid: String) {
        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
        db.collection("users").document(uid)
            .get()
            .addOnSuccessListener { link ->
                val imageLoader = AppImageView()
                val user = link.toObject<UserData>()

                if (user != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
                binding.nicknameTextview.text = user?.nickName

                val imagePath =
                    "images/users/${uid}/${user?.fileName}"
                FirebaseStorage.getInstance().reference
                    .child(imagePath)
                    .downloadUrl
                    .addOnSuccessListener {
                        imageLoader.useLoadPhotoToProfile(
                            imageLink = it.toString(),
                            container = binding.userProfileImageView,
                            imageView = binding.userProfileImageView
                        )
                    }

                binding.nameTextview.text = user?.name ?: ""
                binding.lastnameTextview.text = user?.lastName ?: ""
                binding.emailTextview.text = user?.email ?: ""
                binding.myTelNumberTextview.text = user?.phone ?: ""

                val geoCoder = Geocoder(context, Locale.getDefault())
                if (user?.location?.latitude !== null) {
                    val myPlaceByLocation: List<Address> =
                        user.location?.latitude?.toDouble()
                            ?.let { it1 ->
                                user.location?.longitude?.toDouble()
                                    ?.let { it2 -> geoCoder.getFromLocation(it1, it2, 1) }
                            } as List<Address>
                    val myAddress = myPlaceByLocation[0].getAddressLine(0)
                    binding.myAddressTextview.text = myAddress ?: ""
                }
            }
            .addOnFailureListener {
                showToast("Error Fetching Data!")
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
            }
    }

}