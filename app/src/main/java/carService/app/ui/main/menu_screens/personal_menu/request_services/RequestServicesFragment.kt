package carService.app.ui.main.menu_screens.personal_menu.request_services

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RequestServicesFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RequestServicesFragment(override val layoutId: Int = R.layout.request_services_fragment) :
    BaseFragment<RequestServicesFragmentBinding>() {

    companion object {
        const val TAG = "RequestServicesFragment"
        fun newInstance() = RequestServicesFragment()
    }

    private lateinit var viewModel: RequestServicesViewModel
    private lateinit var navBar: BottomNavigationView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetBehavior(binding.includedBottomSheetLayoutServiceRequest.bottomSheetContainer)
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        binding.addMyCarButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.constraintContainer.alpha = 0.2f
            receiveData()
        }

        val userServicesRequests: RecyclerView = binding.requestServicesRv
        userServicesRequests.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val userServicesRequestsDataAdapter = RequestPersonalServicesAdapter()
        userServicesRequests.adapter = userServicesRequestsDataAdapter

        binding.includedBottomSheetLayoutServiceRequest.addButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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
        val theme = binding.includedBottomSheetLayoutServiceRequest.themeEt.toString().trim()
        val overview = binding.includedBottomSheetLayoutServiceRequest.overviewEt.toString().trim()
        val price = binding.includedBottomSheetLayoutServiceRequest.priceEt
    }
}
