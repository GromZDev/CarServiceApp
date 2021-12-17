package carService.app.ui.main.stock

import androidx.fragment.app.Fragment
import carService.app.R

class StockFragment : Fragment(R.layout.stock_fragment) {

    companion object {
        const val TAG = "StockFragment"
        fun newInstance() = StockFragment()
    }

    private lateinit var viewModel: StockViewModel

}