package carService.app.ui.main.menu_screens.company_menu.announcements

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.CompanyAnnouncementFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompanyAnnouncementFragment : Fragment(R.layout.company_announcement_fragment) {

    companion object {
        const val TAG = "CompanyAnnouncementFragment"
        fun newInstance() = CompanyAnnouncementFragment()
    }

    private val binding: CompanyAnnouncementFragmentBinding by viewBinding()
    private lateinit var viewModel: CompanyAnnouncementViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

        /** Временные данные для наглядности */
        setFakeData()
        /** -------------------------------- */
    }

    private fun setFakeData() {
        val allAnnounce: RecyclerView = binding.companyAnnouncementsRv
        allAnnounce.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val nearCompaniesLayoutManager = allAnnounce.layoutManager as LinearLayoutManager

        val allServicesAdapter = CompanyAnnouncementsAdapter()
        allAnnounce.adapter = allServicesAdapter

        val allServicesList: List<Any> = arrayListOf("2", "6", "5", "1", "1", "1", "1", "1", "1")
        allServicesAdapter.setAllServices(allServicesList)
    }


}