package carService.app.ui.main.person_map

import androidx.fragment.app.Fragment
import carService.app.R

class PersonMapFragment : Fragment(R.layout.person_map_fragment) {

    companion object {
        const val TAG = "PersonMapFragment"
        fun newInstance() = PersonMapFragment()
    }

    private lateinit var viewModel: PersonMapViewModel


}