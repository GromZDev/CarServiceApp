package carService.app.ui.main.menu_screens.personal_menu.person_map

import androidx.fragment.app.Fragment
import carService.app.R

class PersonMapFragment : Fragment(R.layout.person_map_fragment) {

    companion object {
        const val TAG = "PersonMapFragment"
        fun newInstance() = PersonMapFragment()
    }

    private lateinit var viewModel: PersonMapViewModel


}