package carService.app.ui.main.menu_screens.personal_menu.request_services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.data.model.personal.PersonalServicesRequests
import carService.app.databinding.ItemPersonalServiceRequestsRvBinding

class RequestPersonalServicesAdapter :
    RecyclerView.Adapter<RequestPersonalServicesAdapter.RequestPersonalServicesAdapterViewHolder>() {

    private var allRequestsList: MutableList<PersonalServicesRequests> = arrayListOf()

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

    @SuppressLint("NotifyDataSetChanged")
    fun setAllRequests(requests: MutableList<PersonalServicesRequests>) {
        this.allRequestsList = requests
        notifyDataSetChanged()
    }

    fun appendItem(request: PersonalServicesRequests) {
        allRequestsList.add(request)
        notifyItemInserted(itemCount - 1) // С анимацией добавления
    }

    inner class RequestPersonalServicesAdapterViewHolder(
        private val vb: ItemPersonalServiceRequestsRvBinding
    ) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(data: PersonalServicesRequests) = with(vb) {
            vb.itemPersonalServiceRequestsThemeName.text = data.theme
            vb.itemPersonalServiceRequestOverview.text = data.overview
            vb.itemPersonalServiceRequestPrice.text = data.price.toString()
            vb.itemPersonalServiceRequestDate.text = data.data
        }

    }

}
