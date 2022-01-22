package carService.app.ui.main.menu_screens.personal_menu.request_services

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.data.model.personal.PersonalServicesRequests
import carService.app.databinding.RequestServicesFragmentBinding
import carService.app.utils.showToast
import carService.app.utils.showsnackBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class RequestServicesFragment(override val layoutId: Int = R.layout.request_services_fragment) :
    BaseFragment<RequestServicesFragmentBinding>() {

    companion object {
        const val TAG = "RequestServicesFragment"
        fun newInstance() = RequestServicesFragment()
    }

    private val viewModel by viewModel<RequestServicesViewModel>()
    private lateinit var navBar: BottomNavigationView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var userRequest: PersonalServicesRequests = PersonalServicesRequests("", "", -900, "")

    private lateinit var userServices: MutableList<PersonalServicesRequests>
    private lateinit var userServicesRequestsDataAdapter: RequestPersonalServicesAdapter
    private lateinit var recyclerView: RecyclerView
    private val auth: FirebaseAuth by lazy { Firebase.auth }


    override fun initViews() {
        super.initViews()
        setBottomSheetBehavior(binding.includedBottomSheetLayoutServiceRequest.bottomSheetContainer)
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE


        recyclerView = binding.requestServicesRv
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.setHasFixedSize(true)
        userServices = arrayListOf()
        userServicesRequestsDataAdapter = RequestPersonalServicesAdapter()
        recyclerView.adapter = userServicesRequestsDataAdapter


        binding.addMyCarButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.constraintContainer.alpha = 0.2f
        }

        eventChangeListener()

        binding.includedBottomSheetLayoutServiceRequest.addButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            receiveData()
        }

    }

    override fun initViewModel() {
        super.initViewModel()
        doInScope {
            viewModel.newRequest.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                    showToast("Запрос на услугу успешно создан!")
                } else if (it == null && userRequest.data?.isNotEmpty() == true) {
                    showToast("Запрос не создан, что-то пошло не так (")
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    showToast("Запрос не создан, что-то пошло не так (")
                }
            }
        }
        doInScopeResume {
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "" && userRequest.data?.isNotEmpty() == true && userRequest.overview?.isNotEmpty() == true
                    && userRequest.price != -900 && userRequest.data?.isNotEmpty() == true
                ) {
                    view?.showsnackBar(getString(R.string.access_failed))
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }

        doInScope {
            viewModel.servicePersonalRequest.collect {
                if (it != null) {
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                } else if (it == null && userServices.isNotEmpty()) {
                    showToast("что-то пошло не так (")
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "") {
                    showToast("то-то пошло не так (")
                    binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }
        doInScopeResume {
            viewModel.isStateException.collect { isStateException ->
                if (isStateException != "" && userServices.isNotEmpty()
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

    private fun receiveData() {
        val theme = binding.includedBottomSheetLayoutServiceRequest.themeEt.text?.toString()?.trim()
        val overview =
            binding.includedBottomSheetLayoutServiceRequest.overviewEt.text?.toString()?.trim()
        val price = binding.includedBottomSheetLayoutServiceRequest.priceEt.text.toString()
        if (theme.isNullOrEmpty() || overview.isNullOrEmpty() || price.isEmpty()) {
            showToast("Какое-то поле не заполнено")
        }
        if (theme?.isNotEmpty() == true && overview?.isNotEmpty() == true && price.isNotEmpty()) {
            viewModel.updateProfileUser(theme, overview, price, userServicesRequestsDataAdapter)
            binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
        }

    }

    private fun eventChangeListener() {

        val currentUser = auth.currentUser?.uid.toString()
        viewModel.getUserServiceRequests(currentUser, userServicesRequestsDataAdapter)
        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE
    }


}

