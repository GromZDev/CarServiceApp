package carService.app.ui.main.main_screen.company_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R

class CompanyAllServicesAdapter : RecyclerView.Adapter<CompanyAllServicesAdapter.CompanyAllServicesViewHolder>() {

    private var allServicesAdapterList: List<Any> = arrayListOf()
    private lateinit var fragmentManager: FragmentManager // Нужен для получения контекста ниже для
    // передачи в транзакцию

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyAllServicesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_company_all_services_main_rv, parent, false)
        fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager // получаю менеджер
        return CompanyAllServicesViewHolder(view)
    }

    override fun getItemCount(): Int = allServicesAdapterList.size

    override fun onBindViewHolder(holder: CompanyAllServicesViewHolder, position: Int) {
        holder.bind(allServicesAdapterList[position])
    }

    fun setAllServices(actors: List<Any>) {
        this.allServicesAdapterList = actors
        notifyDataSetChanged()
    }

    inner class CompanyAllServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(any: Any) {

        }
    }

}
