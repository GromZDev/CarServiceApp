package carService.app.ui.main.main_screen.company_account

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.MainCompanyFragmentBinding
import carService.app.utils.AppImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainCompanyFragment(
    override val layoutId: Int = R.layout.main_company_fragment
)  :
    BaseFragment<MainCompanyFragmentBinding>() {

    companion object {
        const val TAG = "MainCompanyFragment"
        fun newInstance() = MainCompanyFragment()
    }

    interface OnNearRvItemViewClickListener {
        fun onNearRvItemViewClick()
    }

    private lateinit var viewModel: MainCompanyViewModel

    override fun initViews() {
        super.initViews()

        val navBar: BottomNavigationView =
            requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

        /** Временные данные для наглядности */
        //  setFakeData()
        /** -------------------------------- */
    }

    private fun setFakeData() {
        val allServices: RecyclerView = binding.allCompanyServicesRv
        allServices.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val nearCompaniesLayoutManager = allServices.layoutManager as LinearLayoutManager

        val allServicesAdapter = CompanyAllServicesAdapter(AppImageView())
        allServices.adapter = allServicesAdapter

        val allServicesList: List<Any> = arrayListOf("2", "6", "5", "1", "1", "1", "1", "1", "1")
        //   allServicesAdapter.setAllServices(allServicesList)
    }
}