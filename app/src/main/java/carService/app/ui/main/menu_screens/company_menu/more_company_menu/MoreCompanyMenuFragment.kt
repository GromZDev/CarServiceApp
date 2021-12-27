package carService.app.ui.main.menu_screens.company_menu.more_company_menu

import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.MoreCompanyMenuFragmentBinding
import carService.app.utils.navigate
import carService.app.utils.showsnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreCompanyMenuFragment(
    override val layoutId: Int = R.layout.more_company_menu_fragment
) : BaseFragment<MoreCompanyMenuFragmentBinding>() {

    companion object {
        const val TAG = "MoreCompanyMenuFragment"
        fun newInstance() = MoreCompanyMenuFragment()
    }
    private val vm by viewModel<MoreCompanyMenuViewModel>()

    override fun initViews() {
        binding.exitAccountTextview.setOnClickListener {
            vm.logout()
            navigate(R.id.loginFragment)
        }
    }
}