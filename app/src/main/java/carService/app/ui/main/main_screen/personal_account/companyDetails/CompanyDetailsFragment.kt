package carService.app.ui.main.main_screen.personal_account.companyDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.data.model.organization.OrganisationData
import carService.app.databinding.CompanyDetailsFragmentBinding
import carService.app.ui.main.main_screen.company_account.CompanyAllServicesAdapter
import carService.app.utils.AppImageView
import carService.app.utils.showsnackBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompanyDetailsFragment : Fragment(R.layout.company_details_fragment) {

    companion object {
        const val TAG = "CompanyDetailsFragment"
        fun newInstance() = CompanyDetailsFragment()
    }

    private val binding: CompanyDetailsFragmentBinding by viewBinding()
    private val viewModel: CompanyDetailsViewModel by lazy {
        ViewModelProvider(this)[CompanyDetailsViewModel::class.java]
    }
    private lateinit var navBar: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        /** Временные данные для наглядности */
        //   setFakeData()
        /** -------------------------------- */

        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFullOrganisationMockData()
    }


    private fun renderData(appState: CompanyDetailsAppState) {
        when (appState) {
            is CompanyDetailsAppState.Success -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE

                binding.companyLogoImageView.visibility = View.VISIBLE
                binding.companyServicesTextview.visibility = View.VISIBLE
                binding.companyServicesRv.visibility = View.VISIBLE
                binding.companyRatingTextview.visibility = View.VISIBLE
                binding.companyRating.visibility = View.VISIBLE
                binding.companyOverviewTextview.visibility = View.VISIBLE
                binding.companyOverview.visibility = View.VISIBLE
                navBar.visibility = View.VISIBLE

                setFakeData(appState.organisationData)

            }
            is CompanyDetailsAppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE

                navBar.visibility = View.GONE
                binding.companyLogoImageView.visibility = View.GONE
                binding.companyServicesTextview.visibility = View.GONE
                binding.companyServicesRv.visibility = View.GONE
                binding.companyRatingTextview.visibility = View.GONE
                binding.companyRating.visibility = View.GONE
                binding.companyOverviewTextview.visibility = View.GONE
                binding.companyOverview.visibility = View.GONE


            }
            is CompanyDetailsAppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.mainFragmentView.showsnackBar("Error")
            }
        }
    }


    private fun setFakeData(appState: List<OrganisationData>) {
        val companyAllData: RecyclerView = binding.companyServicesRv
        companyAllData.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val companyAllDataAdapter = CompanyAllServicesAdapter(AppImageView())
        companyAllData.adapter = companyAllDataAdapter

        companyAllDataAdapter.setAllServices(appState)

        binding.companyRating.text = appState[0].rating.toString()

        appState[0].mainOrganisationPhoto?.let {
            AppImageView().useCoilToLoadPhoto(
                imageLink = it,
                container = binding.companyLogoImageView,
                imageView = binding.companyLogoImageView
            )
        }
    }
}