package carService.app.ui.main.main_screen.personal_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R

class CompaniesNearAdapter : RecyclerView.Adapter<CompaniesNearAdapter.CompaniesNearViewHolder>() {

    private var nearCompaniesList: List<Any> = arrayListOf()
    private lateinit var fragmentManager: FragmentManager // Нужен для получения контекста ниже для
    // передачи в транзакцию

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompaniesNearViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_companies_near_rv, parent, false)
        fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager // получаю менеджер
        return CompaniesNearViewHolder(view)
    }

    override fun getItemCount(): Int = nearCompaniesList.size

    override fun onBindViewHolder(holder: CompaniesNearViewHolder, position: Int) {
        holder.bind(nearCompaniesList[position])
    }

    fun setNearCompanies(actors: List<Any>) {
        this.nearCompaniesList = actors
        notifyDataSetChanged()
    }

    inner class CompaniesNearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(any: Any) {

        }
    }

}
