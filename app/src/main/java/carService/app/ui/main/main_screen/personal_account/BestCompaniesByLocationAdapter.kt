package carService.app.ui.main.main_screen.personal_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R

class BestCompaniesByLocationAdapter : RecyclerView.Adapter<BestCompaniesByLocationAdapter.BestCompaniesByLocationViewHolder>() {

    private var bestCompaniesList: List<Any> = arrayListOf()
    private lateinit var fragmentManager: FragmentManager // Нужен для получения контекста ниже для
    // передачи в транзакцию

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestCompaniesByLocationViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_companies_best_rv, parent, false)
        fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager // получаю менеджер
        return BestCompaniesByLocationViewHolder(view)
    }

    override fun getItemCount(): Int = bestCompaniesList.size

    override fun onBindViewHolder(holder: BestCompaniesByLocationViewHolder, position: Int) {
        holder.bind(bestCompaniesList[position])
    }

    fun setBestCompanies(actors: List<Any>) {
        this.bestCompaniesList = actors
        notifyDataSetChanged()
    }

    inner class BestCompaniesByLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(any: Any) {

        }
    }

}
