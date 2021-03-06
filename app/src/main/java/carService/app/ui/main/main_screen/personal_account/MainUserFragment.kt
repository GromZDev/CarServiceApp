package carService.app.ui.main.main_screen.personal_account

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.data.model.organization.OrganisationData
import carService.app.data.model.organization.announcements.OrganisationAnnouncements
import carService.app.databinding.MainUserFragmentBinding
import carService.app.ui.main.main_screen.company_account.MainCompanyFragment
import carService.app.utils.AppImageView
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainUserFragment(
    override val layoutId: Int = R.layout.main_user_fragment
) : BaseFragment<MainUserFragmentBinding>() {

    companion object {
        const val TAG = "MainUserFragment"
        fun newInstance() = MainUserFragment()
    }

    private val viewModel by viewModel<MainUserViewModel>()

    private lateinit var navBar: BottomNavigationView

    override fun initViews() {
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE
    }

    override fun initViewModel() {
        //     doInScope {
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getOrganisationData()
        setFakeData2()
        setFakeData3()
        //       }
    }

    private fun renderData(appState: CompaniesNearAppState) {
        when (appState) {
            is CompaniesNearAppState.Success -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE

                binding.popularServicesRv.visibility = View.VISIBLE
                binding.popularServicesTextview.visibility = View.VISIBLE
                binding.bestCompaniesRv.visibility = View.VISIBLE
                binding.highRatedCompaniesTextview.visibility = View.VISIBLE
                binding.companiesNearRv.visibility = View.VISIBLE
                binding.nearCompaniesTextview.visibility = View.VISIBLE
                binding.doSearchButton.visibility = View.VISIBLE
                binding.searchInputField.visibility = View.VISIBLE
                binding.mainTextview.visibility = View.VISIBLE
                navBar.visibility = View.VISIBLE

                setOrganisationList(appState.organisationData)

            }
            is CompaniesNearAppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE

                navBar.visibility = View.GONE
                binding.popularServicesRv.visibility = View.GONE
                binding.popularServicesTextview.visibility = View.GONE
                binding.bestCompaniesRv.visibility = View.GONE
                binding.highRatedCompaniesTextview.visibility = View.GONE
                binding.companiesNearRv.visibility = View.GONE
                binding.nearCompaniesTextview.visibility = View.GONE
                binding.doSearchButton.visibility = View.GONE
                binding.searchInputField.visibility = View.GONE
                binding.mainTextview.visibility = View.GONE

            }
            is CompaniesNearAppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.mainFragmentRoot.showsnackBar("Error")
            }
        }
    }


    private fun setOrganisationList(appState: List<OrganisationData>) {
        val nearCompanies: RecyclerView = binding.companiesNearRv
        nearCompanies.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val nearCompaniesAdapter = CompaniesNearAdapter(
            AppImageView(),
            object : MainCompanyFragment.OnNearRvItemViewClickListener {
                override fun onNearRvItemViewClick() {
                    val manager = activity?.supportFragmentManager
                    manager?.let {

                        manager.beginTransaction()
                        navigate(R.id.companyDetailsFragment)
                    }
                }

            })
        nearCompanies.adapter = nearCompaniesAdapter
        nearCompaniesAdapter.setNearCompanies(appState)

    }

    private fun setFakeData2() {
        val bestCompanies: RecyclerView = binding.bestCompaniesRv
        bestCompanies.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val orgAnnouncementsAdapter = OrganisationAnnouncementsAdapter(AppImageView())
        bestCompanies.adapter = orgAnnouncementsAdapter

        val bestCompaniesList: List<OrganisationAnnouncements> = arrayListOf(
            OrganisationAnnouncements(),
            OrganisationAnnouncements(),
            OrganisationAnnouncements(),
            OrganisationAnnouncements(),
            OrganisationAnnouncements()
        )
        orgAnnouncementsAdapter.setBestCompanies(bestCompaniesList)
    }

    private fun setFakeData3() {

        val popularServices: RecyclerView = binding.popularServicesRv
        popularServices.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val popularServicesAdapter = PopularServicesMainScreenAdapter()
        popularServices.adapter = popularServicesAdapter

        val popularServicesList: List<Any> = arrayListOf(
            "??????????", "??????????????????", "???????????? ??????????????",
            "??????????????????????", "???????????? ??????????????", "??????????????????", "?????????????????? ????????????", "?????????????????? ??????????????????"
        )
        popularServicesAdapter.setPopularServices(popularServicesList)
    }

}