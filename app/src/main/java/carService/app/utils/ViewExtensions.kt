package carService.app.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import carService.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

fun Fragment.navigate(resId: Int, bundle: Bundle? = null, bundle2: Bundle? = null) {
    NavHostFragment.findNavController(this).navigate(resId, bundle)
}

fun Fragment.hideToolbarAndBottomNav() {
    /** Скрываем навигацию там, где она не нужна */
    val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
    navBar.visibility = View.GONE

    val navBarCompany: BottomNavigationView =
        requireActivity().findViewById(R.id.bottom_company_navigation)
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
    Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .withColor(resources.getColor(R.color.alert_snackbar, null))
        .show()
}

fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
    this.view.setBackgroundColor(colorInt)
    return this
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

fun EditText.validateEmail(email: String): Boolean {
    val VALID_EMAIL_ADDRESS_REGEX: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    val mathcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email)
    return mathcher.find()
}