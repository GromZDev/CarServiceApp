package carService.app.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import carService.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

fun Fragment.navigate(resId: Int, bundle: Bundle? = null, bundle2: Bundle? = null) {
    NavHostFragment.findNavController(this).navigate(resId, bundle)
}

fun Fragment.hideToolbarAndBottomNav() {
    /** Скрываем навигацию там, где она не нужна */
    val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
    navBar.visibility = View.GONE

    val navBarCompany: BottomNavigationView = requireActivity().findViewById(R.id.bottom_company_navigation)
    navBarCompany.visibility = View.GONE

    /** Скрываем тулбар там, где он не нужен */
    val toolBar: Toolbar = requireActivity().findViewById(R.id.toolbar)
    toolBar.visibility = View.GONE
}

fun Fragment.navigate(dir: NavDirections) {
    findNavController().navigate(dir)
}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .addToBackStack(null)
        .replace(R.id.nav_main_fragment, fragment)
        .commit()
}

fun Fragment.replaceFragment(fragment: Fragment) {
    childFragmentManager
        .beginTransaction()
        .addToBackStack(null)
        .replace(R.id.nav_main_fragment, fragment)
        .commit()
}

fun Fragment.showAlertDialog(title: Int, message: Int) {
    AlertDialog.Builder(context).setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }
        .show()
}

fun Fragment.showAlertDialog(body: Int, title: String, message: String) {
    AlertDialog.Builder(context).setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.cancel()

        }
        .setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }
        .show()
}

fun View.showsnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun showToast(context: Context, message: String) {
    /* Функция показывает сообщение */
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}