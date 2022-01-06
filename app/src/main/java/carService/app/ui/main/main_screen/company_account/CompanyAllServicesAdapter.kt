package carService.app.ui.main.main_screen.company_account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.data.model.organization.OrganisationData
import carService.app.databinding.ItemCompanyAllServicesMainRvBinding
import carService.app.utils.AppImageView

class CompanyAllServicesAdapter(
    val imageLoader: AppImageView
) : RecyclerView.Adapter<CompanyAllServicesAdapter.CompanyAllServicesViewHolder>() {

    private var allServicesAdapterList: List<OrganisationData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompanyAllServicesViewHolder(
            ItemCompanyAllServicesMainRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = allServicesAdapterList.size

    override fun onBindViewHolder(holder: CompanyAllServicesViewHolder, position: Int) {
        holder.bind(allServicesAdapterList[position])
    }

    fun setAllServices(allServices: List<OrganisationData>) {
        this.allServicesAdapterList = allServices
        notifyDataSetChanged()
    }

    inner class CompanyAllServicesViewHolder(private val vb: ItemCompanyAllServicesMainRvBinding) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(organisationData: OrganisationData) = with(vb) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                organisationData.allServices?.get(0)?.serviceMainPhoto.let {
                    if (it != null) {
                        imageLoader.useCoilToLoadPhoto(
                            imageLink = it,
                            container = itemServiceImage,
                            imageView = itemServiceImage
                        )
                    }
                }

                itemServiceName.text = organisationData.allServices?.get(0)?.serviceName

                itemServicePrice.text = organisationData.allServices?.get(0)?.price.toString()
            }
        }
    }

}
