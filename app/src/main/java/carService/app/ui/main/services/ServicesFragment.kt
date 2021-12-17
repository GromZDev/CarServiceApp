package carService.app.ui.main.services

import androidx.fragment.app.Fragment
import carService.app.R

class ServicesFragment : Fragment(R.layout.services_fragment) {

    companion object {
        const val TAG = "ServicesFragment"
        fun newInstance() = ServicesFragment()
    }

    private lateinit var viewModel: ServicesViewModel


}