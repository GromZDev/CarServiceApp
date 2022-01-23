package carService.app.ui.main.menu_screens.personal_menu.request_services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.data.model.personal.PersonalServicesRequests
import carService.app.databinding.ItemPersonalServiceRequestsRvBinding

class RequestPersonalServicesAdapter :
    RecyclerView.Adapter<RequestPersonalServicesAdapter.RequestPersonalServicesAdapterViewHolder>(),
    ItemTouchHelperAdapter {

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
        RecyclerView.ViewHolder(vb.root), ItemTouchHelperViewHolder {

        fun bind(data: PersonalServicesRequests) = with(vb) {
            vb.itemPersonalServiceRequestsThemeName.text = data.theme
            vb.itemPersonalServiceRequestOverview.text = data.overview
            vb.itemPersonalServiceRequestPrice.text = data.price.toString()
            vb.itemPersonalServiceRequestDate.text = data.data
        }

        override fun onItemSelected() {
            itemView.setBackgroundResource(R.drawable.item_personal_service_requests_recycler_remove_background)
        }

        override fun onItemClear() {
            itemView.setBackgroundResource(R.drawable.item_near_companies_recycler_background)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemDismiss(position: Int) {
        allRequestsList.removeAt(position)
        notifyItemRemoved(position)
    }
}