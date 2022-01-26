package carService.app.ui.main.menu_screens.personal_menu.more_menu

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.MoreMenuFragmentBinding
import carService.app.ui.main.menu_screens.company_menu.more_company_menu.MoreCompanyMenuViewModel
import carService.app.utils.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreMenuFragment(
    override val layoutId: Int = R.layout.more_menu_fragment
) : BaseFragment<MoreMenuFragmentBinding>() {
    companion object {
        const val TAG = "MoreMenuFragment"
        fun newInstance() = MoreMenuFragment()
    }

    private val vm by viewModel<MoreCompanyMenuViewModel>()

    private val shareSuccess =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        }

    override fun initViews() {
        binding.shareButton.setOnClickListener {
            shareApp()
        }

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

    private fun shareApp() {
        val (url, intentSubject, intentBody) = getStringsText()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, intentSubject)
        intent.putExtra(Intent.EXTRA_TEXT, intentBody + "\n" + url)
        shareSuccess.launch(intent)
    }

    private fun getStringsText(): Triple<String, String, String> {
        val url = getString(R.string.share_app_url)
        val intentSubject = getString(R.string.share_app_title)
        val intentBody = getString(R.string.share_app_body)
        return Triple(url, intentSubject, intentBody)
    }
}