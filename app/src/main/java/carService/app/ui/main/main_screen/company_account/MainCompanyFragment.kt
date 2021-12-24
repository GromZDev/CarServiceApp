package carService.app.ui.main.main_screen.company_account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.MainCompanyFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainCompanyFragment : Fragment(R.layout.main_company_fragment) {

    companion object {
        const val TAG = "MainCompanyFragment"
        fun newInstance() = MainCompanyFragment()
    }

    private val binding: MainCompanyFragmentBinding by viewBinding()
    private lateinit var viewModel: MainCompanyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

        /** Временные данные для наглядности */
        setFakeData()
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

        val allServicesAdapter = CompanyAllServicesAdapter()
        allServices.adapter = allServicesAdapter

        val allServicesList: List<Any> = arrayListOf("2", "6", "5", "1", "1", "1", "1", "1", "1")
        allServicesAdapter.setAllServices(allServicesList)
    }
}