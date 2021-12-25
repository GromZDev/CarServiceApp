package carService.app.ui.main.menu_screens.personal_menu.request_services

import androidx.fragment.app.Fragment
import carService.app.R

class RequestServicesFragment : Fragment(R.layout.request_services_fragment) {

    companion object {
        const val TAG = "RequestServicesFragment"
        fun newInstance() = RequestServicesFragment()
    }

    private lateinit var viewModel: RequestServicesViewModel


}