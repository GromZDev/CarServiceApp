package carService.app.ui.main.products

import androidx.fragment.app.Fragment
import carService.app.R

class ProductsFragment : Fragment(R.layout.products_fragment) {

    companion object {
        const val TAG = "ProductsFragment"
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel

}