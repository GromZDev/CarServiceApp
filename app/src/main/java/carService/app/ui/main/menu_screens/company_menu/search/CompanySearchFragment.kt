package carService.app.ui.main.menu_screens.company_menu.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import carService.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompanySearchFragment : Fragment(R.layout.company_search_fragment) {

    companion object {
        const val TAG = "CompanySearchFragment"
        fun newInstance() = CompanySearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_company_navigation)
        navBar.visibility = View.VISIBLE

    }
}