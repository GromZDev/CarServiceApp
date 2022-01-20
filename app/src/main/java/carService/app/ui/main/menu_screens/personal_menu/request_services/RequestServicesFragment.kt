package carService.app.ui.main.menu_screens.personal_menu.request_services

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.RequestServicesFragmentBinding
import carService.app.ui.main.main_screen.company_account.CompanyAllServicesAdapter
import carService.app.utils.AppImageView
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
        }

//        val companyAllData: RecyclerView = binding.requestServicesRv
//        companyAllData.layoutManager = LinearLayoutManager(
//            context,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//
//        val companyAllDataAdapter = CompanyAllServicesAdapter(AppImageView())
//        companyAllData.adapter = companyAllDataAdapter

    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

    }


}