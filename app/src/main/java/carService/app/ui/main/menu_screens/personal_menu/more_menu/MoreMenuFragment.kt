package carService.app.ui.main.menu_screens.personal_menu.more_menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.databinding.MoreMenuFragmentBinding
import carService.app.utils.showToast

class MoreMenuFragment : Fragment(R.layout.more_menu_fragment) {

    companion object {
        const val TAG = "MoreMenuFragment"
        fun newInstance() = MoreMenuFragment()
    }

    private val binding: MoreMenuFragmentBinding by viewBinding()
    private lateinit var viewModel: MoreMenuViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shareButton.setOnClickListener {
            showToast("Well Done!")
        }
    }

}