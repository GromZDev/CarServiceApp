package carService.app.ui.main.menu_screens.company_menu.announcements

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.data.model.organization.announcements.OrganisationAnnouncements
import carService.app.data.model.personal.PersonalServicesRequests
import carService.app.databinding.CompanyAnnouncementFragmentBinding
import carService.app.ui.main.menu_screens.personal_menu.request_services.RequestPersonalServicesAdapter
import carService.app.ui.main.menu_screens.personal_menu.request_services.RequestServicesViewModel
import carService.app.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

private const val PERMISSION_STORAGE = 189

@KoinApiExtension
class CompanyAnnouncementFragment(override val layoutId: Int = R.layout.company_announcement_fragment) :
    BaseFragment<CompanyAnnouncementFragmentBinding>() {

    companion object {
        const val TAG = "CompanyAnnouncementFragment"
        fun newInstance() = CompanyAnnouncementFragment()
    }

    private val viewModel by viewModel<CompanyAnnouncementViewModel>()
    private lateinit var navBar: BottomNavigationView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var recyclerView: RecyclerView
    private lateinit var companyServices: MutableList<OrganisationAnnouncements>
    private lateinit var companyAnnouncementsDataAdapter: CompanyAnnouncementsAdapter
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private var imageUri: Uri? = null
    private var userRequest: OrganisationAnnouncements = OrganisationAnnouncements(
        "", 0, "", "", -900, "", "")

    private val getActionPhotoFromStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { pic ->
            imageUri = pic?.data?.data
            binding.includedBottomSheetLayoutCompanyServices.youImageIw.setImageURI(imageUri)
        }

    override fun initViews() {
        super.initViews()
        setBottomSheetBehavior(binding.includedBottomSheetLayoutCompanyServices.bottomSheetContainerConstraint)
        navBar = requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

        binding.companyAnnouncementAddingFab.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.constraintContainer.alpha = 0.2f
        }

        binding.includedBottomSheetLayoutCompanyServices.addButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            saveServicesData()

        }

        binding.includedBottomSheetLayoutCompanyServices.makePhotoButton.setOnClickListener {
            checkForPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                "Storage",
                PERMISSION_STORAGE
            )
        }

        recyclerView = binding.companyAnnouncementsRv
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.setHasFixedSize(true)
        companyServices = arrayListOf()
        companyAnnouncementsDataAdapter = CompanyAnnouncementsAdapter(AppImageView())
        recyclerView.adapter = companyAnnouncementsDataAdapter

//        if (companyAnnouncementsDataAdapter.itemCount < 0) {
//            binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
//        }
        getData()
    }

    override fun initViewModel() {
        super.initViewModel()
        doInScope {
            viewModel.newService.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    showToast("Объявление успешно создано!")
                    binding.includedBottomSheetLayoutCompanyServices.themeEt.text?.clear()
                    binding.includedBottomSheetLayoutCompanyServices.overviewEt.text?.clear()
                    binding.includedBottomSheetLayoutCompanyServices.priceEt.text?.clear()
                } else if (it == null && userRequest.data?.isNotEmpty() == true) {
                    showToast("Объявление не создано, что-то пошло не так (")
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    showToast("Объявление не создано, что-то пошло не так (")
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
        doInScopeResume {
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "" && userRequest.data?.isNotEmpty() == true && userRequest.data?.isNotEmpty() == true
                    && userRequest.price != -900 && userRequest.data?.isNotEmpty() == true) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }

        getListData()
    }

    private fun getListData() {
        doInScope {
            viewModel.serviceCompanyAnnouncement.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                } else if (it == null && companyServices.isNotEmpty()) {
                    showToast("что-то пошло не так (")
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                } else if (it == null || companyServices.isEmpty()) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            viewModel.isStateException2.collect { isStateException ->
                if (isStateException != "") {
                    showToast("то-то пошло не так (")
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
        doInScopeResume {
            viewModel.isStateException2.collect { isStateException ->
                if (isStateException != "" && companyServices.isNotEmpty()
                ) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                    navBar.visibility = View.VISIBLE
                } else {
                    navBar.visibility = View.GONE
                }
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> binding.constraintContainer.alpha = 1.0f
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.constraintContainer.alpha = 1.0f
                    BottomSheetBehavior.STATE_DRAGGING -> {}
                    BottomSheetBehavior.STATE_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                    BottomSheetBehavior.STATE_SETTLING -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun saveServicesData() {
        val theme = binding.includedBottomSheetLayoutCompanyServices.themeEt.text?.toString()?.trim()
        val overview =
            binding.includedBottomSheetLayoutCompanyServices.overviewEt.text?.toString()?.trim()
        val price = binding.includedBottomSheetLayoutCompanyServices.priceEt.text.toString()

        val currentUser = auth.currentUser?.uid.toString()

        if (theme.isNullOrEmpty() || overview.isNullOrEmpty() || price.isEmpty() || imageUri == null) {
            showToast("Какое-то поле не заполнено")
            binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
        }
        if (theme?.isNotEmpty() == true && overview?.isNotEmpty() == true && price.isNotEmpty() && imageUri != null) {
            imageUri?.let {
                viewModel.updateOrganisationServiceList(theme, overview, price, currentUser,
                    it, companyAnnouncementsDataAdapter)
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun selectPhotoFromStorage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        try {
            getActionPhotoFromStorage.launch(intent)
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
            PERMISSION_STORAGE -> innerCheck("Storage")
        }
    }

    private fun getData() {
        val currentUser = auth.currentUser?.uid.toString()
        viewModel.gerAnnouncementsData(currentUser, companyAnnouncementsDataAdapter)
        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
    }


}