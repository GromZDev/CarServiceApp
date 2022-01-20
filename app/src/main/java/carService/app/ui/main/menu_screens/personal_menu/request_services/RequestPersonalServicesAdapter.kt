package carService.app.ui.main.menu_screens.personal_menu.request_services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import carService.app.databinding.ItemPersonalServiceRequestsRvBinding

class RequestPersonalServicesAdapter :
    RecyclerView.Adapter<RequestPersonalServicesAdapter.RequestPersonalServicesAdapterViewHolder>() {

    private var allRequestsList: List<Any> = arrayListOf()
    private lateinit var fragmentManager: FragmentManager

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RequestPersonalServicesAdapterViewHolder(
        ItemPersonalServiceRequestsRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

    )

    override fun getItemCount(): Int = allRequestsList.size

    override fun onBindViewHolder(holder: RequestPersonalServicesAdapterViewHolder, position: Int) {
        holder.bind(allRequestsList[position])
    }

    fun setAllRequests(actors: List<Any>) {
        this.allRequestsList = actors
        notifyDataSetChanged()
    }

    inner class RequestPersonalServicesAdapterViewHolder(
        private val vb: ItemPersonalServiceRequestsRvBinding
    ) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(any: Any) = with(vb) {

        }
    }

}
