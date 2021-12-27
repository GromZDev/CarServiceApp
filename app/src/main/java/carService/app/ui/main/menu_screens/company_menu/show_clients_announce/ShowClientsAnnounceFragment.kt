package carService.app.ui.main.menu_screens.company_menu.show_clients_announce

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.ShowClientsAnnounceFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShowClientsAnnounceFragment : Fragment(R.layout.show_clients_announce_fragment) {

    companion object {
        const val TAG = "ShowClientsAnnounceFragment"
        fun newInstance() = ShowClientsAnnounceFragment()
    }

    private val binding: ShowClientsAnnounceFragmentBinding by viewBinding()
    private lateinit var viewModel: ShowClientsAnnounceViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

        /** Временные данные для наглядности */
        setFakeData()
        /** -------------------------------- */

    }

    private fun setFakeData() {
        val allClientsWishes: RecyclerView = binding.showClientsAnnounceRv
        allClientsWishes.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val nearCompaniesLayoutManager = allClientsWishes.layoutManager as LinearLayoutManager

        val allServicesAdapter = ShowClientsAnnounceAdapter()
        allClientsWishes.adapter = allServicesAdapter

        val allServicesList: List<Any> = arrayListOf("2", "6", "5", "1", "1", "1", "1", "1", "1")
        allServicesAdapter.setAllServices(allServicesList)
    }


}