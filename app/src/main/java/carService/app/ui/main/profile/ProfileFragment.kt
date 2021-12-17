package carService.app.ui.main.profile

import androidx.fragment.app.Fragment
import carService.app.R

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    companion object {
        const val TAG = "ProfileFragment"
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel


}