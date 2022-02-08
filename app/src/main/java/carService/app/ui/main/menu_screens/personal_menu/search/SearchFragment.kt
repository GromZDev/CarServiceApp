package carService.app.ui.main.menu_screens.personal_menu.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import carService.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment(R.layout.search_fragment) {

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

    }
}