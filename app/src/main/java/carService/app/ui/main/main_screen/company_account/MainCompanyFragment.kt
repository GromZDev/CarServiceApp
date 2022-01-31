package carService.app.ui.main.main_screen.company_account

import android.location.Address
import android.location.Geocoder
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.data.model.UserData
import carService.app.data.model.organization.OrganisationData
import carService.app.databinding.MainCompanyFragmentBinding
import carService.app.di.FireBaseModule
import carService.app.utils.AppImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import java.util.*

@KoinApiExtension
class MainCompanyFragment(
    override val layoutId: Int = R.layout.main_company_fragment
)  :
    BaseFragment<MainCompanyFragmentBinding>() {

    companion object {
        const val TAG = "MainCompanyFragment"
        fun newInstance() = MainCompanyFragment()
    }

    private val fireBase by inject<FireBaseModule>()

    interface OnNearRvItemViewClickListener {
        fun onNearRvItemViewClick()
    }

    override fun initViews() {
        super.initViews()

        val navBar: BottomNavigationView =
            requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

        val currentUser = fireBase.provideFireBaseAuth.currentUser?.uid.toString()
        if (currentUser.isNotEmpty()) {
            getCompanyData(currentUser)
        }
    }

    private fun getCompanyData(uid: String) {
        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
        fireBase.provideFirebaseFirestore.collection("organisationAccount").document(uid)
            .get()
            .addOnSuccessListener { link ->
                val imageLoader = AppImageView()
                val user = link.toObject<UserData>()

                if (user != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
                binding.companyNicknameTextview.text = user?.nickName.toString()


                val imagePath =
                    "images/users/${uid}/${user?.fileName}"
                FirebaseStorage.getInstance().reference
                    .child(imagePath)
                    .downloadUrl
                    .addOnSuccessListener {
                        imageLoader.useLoadPhotoToProfile(
                            imageLink = it.toString(),
                            container = binding.companyImageView,
                            imageView = binding.companyImageView
                        )
                    }

                binding.companyNameTextview.text = user?.name
                binding.companyLastnameTextview.text = user?.lastName
                binding.emailTextview.text = user?.email

                val geoCoder = Geocoder(context, Locale.getDefault())
                if (user?.location?.latitude !== null) {
                    val myPlaceByLocation: List<Address> =
                        user.location?.latitude?.toDouble()
                            ?.let { it1 ->
                                user.location?.longitude?.toDouble()
                                    ?.let { it2 -> geoCoder.getFromLocation(it1, it2, 1) }
                            } as List<Address>
                    val myAddress = myPlaceByLocation[0].getAddressLine(0)
                    binding.organisationAddressTextview.text = myAddress ?: ""
                }
            }
    }
}