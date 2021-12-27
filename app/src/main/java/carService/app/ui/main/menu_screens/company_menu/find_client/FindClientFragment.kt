package carService.app.ui.main.menu_screens.company_menu.find_client

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.FindClientFragmentBinding
import carService.app.databinding.ShowClientsAnnounceFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FindClientFragment : Fragment(R.layout.find_client_fragment) {

    companion object {
        const val TAG = "FindClientFragment"
        fun newInstance() = FindClientFragment()
    }

    private val binding: FindClientFragmentBinding by viewBinding()
    private lateinit var viewModel: FindClientViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

    }
}