package carService.app.ui.main.main_screen.personal_account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.MainUserFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainUserFragment : Fragment(R.layout.main_user_fragment) {

    companion object {
        const val TAG = "MainUserFragment"
        fun newInstance() = MainUserFragment()
    }

    private val binding: MainUserFragmentBinding by viewBinding()
    private lateinit var viewModel: MainUserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Скрываем навигацию там, где она не нужна */
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        /** Временные данные для наглядности */
        setFakeData()
        setFakeData2()
        setFakeData3()
        /** -------------------------------- */
    }

    private fun setFakeData() {
        val nearCompanies: RecyclerView = binding.companiesNearRv
        nearCompanies.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val nearCompaniesLayoutManager = nearCompanies.layoutManager as LinearLayoutManager

        val nearCompaniesAdapter = CompaniesNearAdapter()
        nearCompanies.adapter = nearCompaniesAdapter

        val nearCompaniesList: List<Any> = arrayListOf("2", "6", "5", "1", "1", "1", "1", "1", "1")
        nearCompaniesAdapter.setNearCompanies(nearCompaniesList)
    }

    private fun setFakeData2() {
        val bestCompanies: RecyclerView = binding.bestCompaniesRv
        bestCompanies.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val bestCompaniesLayoutManager = bestCompanies.layoutManager as LinearLayoutManager

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
        val popularServicesLayoutManager: LinearLayoutManager =
            popularServices.layoutManager as LinearLayoutManager

        val popularServicesAdapter = PopularServicesMainScreenAdapter()
        popularServices.adapter = popularServicesAdapter

        val popularServicesList: List<Any> = arrayListOf(
            "Шумка", "Полировка", "Ремонт коробки",
            "Диагностика", "Ремонт ходовой", "Детейлинг", "Перетяжка салона", "Установка динамиков"
        )
        popularServicesAdapter.setPopularServices(popularServicesList)
    }

}