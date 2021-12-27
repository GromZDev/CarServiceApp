package carService.app.ui.main.menu_screens.company_menu.announcements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R

class CompanyAnnouncementsAdapter :
    RecyclerView.Adapter<CompanyAnnouncementsAdapter.CompanyAnnouncementsViewHolder>() {

    private var allAnnounceAdapterList: List<Any> = arrayListOf()
    private lateinit var fragmentManager: FragmentManager // Нужен для получения контекста ниже для
    // передачи в транзакцию

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompanyAnnouncementsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_company_all_services_main_rv, parent, false)
        fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager // получаю менеджер
        return CompanyAnnouncementsViewHolder(view)
    }

    override fun getItemCount(): Int = allAnnounceAdapterList.size

    override fun onBindViewHolder(holder: CompanyAnnouncementsViewHolder, position: Int) {
        holder.bind(allAnnounceAdapterList[position])
    }

    fun setAllServices(actors: List<Any>) {
        this.allAnnounceAdapterList = actors
        notifyDataSetChanged()
    }

    inner class CompanyAnnouncementsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(any: Any) {

        }
    }

}
