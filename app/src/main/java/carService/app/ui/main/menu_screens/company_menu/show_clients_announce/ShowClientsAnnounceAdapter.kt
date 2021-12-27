package carService.app.ui.main.menu_screens.company_menu.show_clients_announce

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R

class ShowClientsAnnounceAdapter :
    RecyclerView.Adapter<ShowClientsAnnounceAdapter.FindClientsAdapterViewHolder>() {

    private var allClientsWishList: List<Any> = arrayListOf()
    private lateinit var fragmentManager: FragmentManager // Нужен для получения контекста ниже для
    // передачи в транзакцию

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FindClientsAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_clients_announcements_rv, parent, false)
        fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager // получаю менеджер
        return FindClientsAdapterViewHolder(view)
    }

    override fun getItemCount(): Int = allClientsWishList.size

    override fun onBindViewHolder(holder: FindClientsAdapterViewHolder, position: Int) {
        holder.bind(allClientsWishList[position])
    }

    fun setAllServices(actors: List<Any>) {
        this.allClientsWishList = actors
        notifyDataSetChanged()
    }

    inner class FindClientsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(any: Any) {

        }
    }

}
