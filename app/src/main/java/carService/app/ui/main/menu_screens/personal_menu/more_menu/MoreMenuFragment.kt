package carService.app.ui.main.menu_screens.personal_menu.more_menu

import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.MoreMenuFragmentBinding
import carService.app.ui.main.menu_screens.company_menu.more_company_menu.MoreCompanyMenuViewModel
import carService.app.utils.navigate
import carService.app.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreMenuFragment(
        override val layoutId: Int = R.layout.more_menu_fragment
    ) : BaseFragment<MoreMenuFragmentBinding>() {
    companion object {
        const val TAG = "MoreMenuFragment"
        fun newInstance() = MoreMenuFragment()
    }
    private val vm by viewModel<MoreCompanyMenuViewModel>()

    override fun initViews() {
        binding.shareButton.setOnClickListener {
            showToast("Well Done!")
        }

        binding.exitAccountTextview.setOnClickListener {
            vm.logout()
            navigate(R.id.loginFragment)
        }
    }
}