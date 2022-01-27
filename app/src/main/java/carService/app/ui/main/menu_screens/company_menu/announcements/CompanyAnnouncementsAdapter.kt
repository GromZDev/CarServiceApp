package carService.app.ui.main.menu_screens.company_menu.announcements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.data.model.organization.announcements.OrganisationAnnouncements
import carService.app.databinding.ItemCompanyAllServicesMainRvBinding
import carService.app.utils.AppImageView
import carService.app.utils.ItemTouchHelperViewHolder
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class CompanyAnnouncementsAdapter(
    val imageLoader: AppImageView
) :
    RecyclerView.Adapter<CompanyAnnouncementsAdapter.CompanyAnnouncementsViewHolder>() {

    private var allAnnounceAdapterList: MutableList<OrganisationAnnouncements> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CompanyAnnouncementsViewHolder(
        ItemCompanyAllServicesMainRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    fun appendItem(request: OrganisationAnnouncements) {
        allAnnounceAdapterList.add(request)
        notifyItemInserted(itemCount - 1) // С анимацией добавления
    }

    override fun getItemCount(): Int = allAnnounceAdapterList.size

    override fun onBindViewHolder(holder: CompanyAnnouncementsViewHolder, position: Int) {
        holder.bind(allAnnounceAdapterList[position])
    }

    fun setAllServices(announce: MutableList<OrganisationAnnouncements>) {
        this.allAnnounceAdapterList = announce
        notifyDataSetChanged()
    }

    inner class CompanyAnnouncementsViewHolder(
        private val vb: ItemCompanyAllServicesMainRvBinding
    ) : RecyclerView.ViewHolder(vb.root),
        ItemTouchHelperViewHolder {


        fun bind(data: OrganisationAnnouncements) = with(vb) {
            vb.itemServiceName.text = data.serviceName ?: ""
            vb.itemServicePrice.text = data.price.toString()

            data.servicePhoto?.let {
                imageLoader.useCoilToLoadPhoto(
                    imageLink = it,
                    container = itemServiceImage,
                    imageView = itemServiceImage
                )
            }


        }

        override fun onItemSelected() {
            TODO("Not yet implemented")
        }

        override fun onItemClear() {
            TODO("Not yet implemented")
        }
    }

}
