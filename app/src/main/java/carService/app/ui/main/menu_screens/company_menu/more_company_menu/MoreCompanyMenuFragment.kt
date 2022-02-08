package carService.app.ui.main.menu_screens.company_menu.more_company_menu

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.MoreCompanyMenuFragmentBinding
import carService.app.utils.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreCompanyMenuFragment(
    override val layoutId: Int = R.layout.more_company_menu_fragment
) : BaseFragment<MoreCompanyMenuFragmentBinding>() {

    companion object {
        const val TAG = "MoreCompanyMenuFragment"
        fun newInstance() = MoreCompanyMenuFragment()
    }

    private val vm by viewModel<MoreCompanyMenuViewModel>()

    private val shareSuccess =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        }

    override fun initViews() {
        binding.exitAccountTextview.setOnClickListener {
            vm.logout()
//            clearApplicatione()
            navigate(R.id.loginFragment)
        }

        binding.shareButton.setOnClickListener {
            shareApp()
        }

        binding.profileTextview.setOnClickListener {
            navigate(R.id.companyProfileFragment)
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