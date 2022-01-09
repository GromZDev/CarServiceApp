package carService.app.ui.main.main_screen.company_account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.data.model.organization.OrganisationServiceList
import carService.app.databinding.ItemCompanyAllServicesDetailsRvBinding
import carService.app.utils.AppImageView

class CompanyAllServicesAdapter(
    val imageLoader: AppImageView
) : RecyclerView.Adapter<CompanyAllServicesAdapter.CompanyAllServicesViewHolder>() {

    private var allServicesAdapterList: List<OrganisationServiceList> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompanyAllServicesViewHolder(
            ItemCompanyAllServicesDetailsRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = allServicesAdapterList.size

    override fun onBindViewHolder(holder: CompanyAllServicesViewHolder, position: Int) {
        holder.bind(allServicesAdapterList[position])
    }

    fun setAllServices(allServices: List<OrganisationServiceList>) {
        this.allServicesAdapterList = allServices
        notifyDataSetChanged()
    }

    inner class CompanyAllServicesViewHolder(private val vb: ItemCompanyAllServicesDetailsRvBinding) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(organisationData: OrganisationServiceList) = with(vb) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                organisationData.serviceMainPhoto.let {
                    if (it != null) {
                        imageLoader.useCoilToLoadPhoto(
                            imageLink = it,
                            container = itemServiceImage,
                            imageView = itemServiceImage
                        )
                    }
                }

                itemServiceName.text = organisationData.serviceName

                itemServicePrice.text = organisationData.price.toString()
            }
        }
    }

}
