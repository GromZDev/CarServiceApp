package carService.app.ui.main.main_screen.personal_account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.data.model.UserData
import carService.app.databinding.MainUserFragmentBinding
import carService.app.ui.main.main_screen.company_account.MainCompanyFragment
import carService.app.utils.AppImageView
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainUserFragment : Fragment(R.layout.main_user_fragment) {

    companion object {
        const val TAG = "MainUserFragment"
        fun newInstance() = MainUserFragment()
    }

    private val binding: MainUserFragmentBinding by viewBinding()

    private val viewModel: MainUserViewModel by lazy {
        ViewModelProvider(this)[MainUserViewModel::class.java]
    }

    private lateinit var navBar: BottomNavigationView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Скрываем навигацию там, где она не нужна */
        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        /** Временные данные для наглядности */
        setFakeData2()
        setFakeData3()
        /** -------------------------------- */

        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getOrganisationMockData()
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

                setFakeData(appState.organisationData)

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
                binding.mainFragmentView.showsnackBar("Error")
            }
        }
    }

    private fun setFakeData(appState: List<UserData>) {
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

        val bestCompaniesAdapter = BestCompaniesByLocationAdapter()
        bestCompanies.adapter = bestCompaniesAdapter

        val bestCompaniesList: List<Any> = arrayListOf("2", "6", "5", "t", "y4", "1", "1", "1")
        bestCompaniesAdapter.setBestCompanies(bestCompaniesList)
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
            "Шумка", "Полировка", "Ремонт коробки",
            "Диагностика", "Ремонт ходовой", "Детейлинг", "Перетяжка салона", "Установка динамиков"
        )
        popularServicesAdapter.setPopularServices(popularServicesList)
    }

}