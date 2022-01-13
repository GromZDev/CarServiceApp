package carService.app.ui.main.menu_screens.company_menu.more_company_menu

import android.app.ActivityManager
import android.content.Context
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
//            clearApplicatione()
            navigate(R.id.loginFragment)
        }
    }

    /**
     * Удаляем все Permissions выходим из приложения, full Logout
     */
    private fun clearApplicatione() {
        val manager = requireContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        manager.clearApplicationUserData()
    }
}