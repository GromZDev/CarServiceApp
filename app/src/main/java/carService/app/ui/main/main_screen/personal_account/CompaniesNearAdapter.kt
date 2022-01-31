package carService.app.ui.main.main_screen.personal_account

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.data.model.organization.OrganisationData
import carService.app.databinding.ItemCompaniesNearRvBinding
import carService.app.ui.main.main_screen.company_account.MainCompanyFragment
import carService.app.utils.AppImageView
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class CompaniesNearAdapter(
    val imageLoader: AppImageView,
    private var onItemViewClickListener: MainCompanyFragment.OnNearRvItemViewClickListener?
) : RecyclerView.Adapter<CompaniesNearAdapter.CompaniesNearViewHolder>() {

    private var nearCompaniesList: List<OrganisationData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CompaniesNearViewHolder(
        ItemCompaniesNearRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun getItemCount(): Int = nearCompaniesList.size

    override fun onBindViewHolder(holder: CompaniesNearViewHolder, position: Int) {
        holder.bind(nearCompaniesList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNearCompanies(organisationsNear: List<OrganisationData>) {
        this.nearCompaniesList = organisationsNear
        notifyDataSetChanged()
    }

    inner class CompaniesNearViewHolder(private val vb: ItemCompaniesNearRvBinding) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(organisation: OrganisationData) = with(vb) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemCompanyName.text = organisation.name
                itemCompanyMainService.text = organisation.email

                organisation.mainOrganisationPhoto?.let {
                    imageLoader.useCoilToLoadPhoto(
                        imageLink = it,
                        container = itemCompanyImage,
                        imageView = itemCompanyImage
                    )
                }

                itemCompanyRating.text = organisation.rating.toString()

                itemView.setOnClickListener {
                    onItemViewClickListener?.onNearRvItemViewClick() // Вызываем слушатель нажатия
                }
            }
        }
    }

}
