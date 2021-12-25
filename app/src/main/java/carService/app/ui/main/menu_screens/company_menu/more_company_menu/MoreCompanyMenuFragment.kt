package carService.app.ui.main.menu_screens.company_menu.more_company_menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.MoreCompanyMenuFragmentBinding
import carService.app.databinding.MoreMenuFragmentBinding
import carService.app.utils.showToast

class MoreCompanyMenuFragment : Fragment(R.layout.more_company_menu_fragment) {

    companion object {
        const val TAG = "MoreCompanyMenuFragment"
        fun newInstance() = MoreCompanyMenuFragment()
    }

    private val binding: MoreCompanyMenuFragmentBinding by viewBinding()
    private lateinit var viewModel: MoreCompanyMenuViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}