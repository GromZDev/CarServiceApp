package carService.app.ui.main.main_screen.personal_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.R

class PopularServicesMainScreenAdapter : RecyclerView.Adapter<PopularServicesMainScreenAdapter.PopularServicesMainScreenViewHolder>() {

    private var popularServicesMainScreenList: List<Any> = arrayListOf()
    private lateinit var fragmentManager: FragmentManager // Нужен для получения контекста ниже для
    // передачи в транзакцию

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularServicesMainScreenViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_popular_services_main_rv, parent, false)
        fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager // получаю менеджер
        return PopularServicesMainScreenViewHolder(view)
    }

    override fun getItemCount(): Int = popularServicesMainScreenList.size

    override fun onBindViewHolder(holder: PopularServicesMainScreenViewHolder, position: Int) {
        holder.bind(popularServicesMainScreenList[position])
    }

    fun setPopularServices(actors: List<Any>) {
        this.popularServicesMainScreenList = actors
        notifyDataSetChanged()
    }

    inner class PopularServicesMainScreenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(any: Any) {

        }
    }

}
