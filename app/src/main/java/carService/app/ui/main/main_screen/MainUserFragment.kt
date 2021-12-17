package carService.app.ui.main.main_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import carService.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainUserFragment : Fragment(R.layout.main_user_fragment) {

    companion object {
        const val TAG = "MainUserFragment"
        fun newInstance() = MainUserFragment()
    }

    private lateinit var viewModel: MainUserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Скрываем навигацию там, где она не нужна */
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

    }

}