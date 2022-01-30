package carService.app.ui.main.main_screen.personal_account

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.data.model.organization.announcements.OrganisationAnnouncements
import carService.app.databinding.ItemCompaniesAnnouncementsRvBinding
import carService.app.utils.AppImageView
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class OrganisationAnnouncementsAdapter(
    val imageLoader: AppImageView
) : RecyclerView.Adapter<OrganisationAnnouncementsAdapter.OrganisationAnnouncementsViewHolder>() {

    private var bestCompaniesList: List<OrganisationAnnouncements> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrganisationAnnouncementsViewHolder(
            ItemCompaniesAnnouncementsRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int = bestCompaniesList.size

    override fun onBindViewHolder(holder: OrganisationAnnouncementsViewHolder, position: Int) {
        holder.bind(bestCompaniesList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setBestCompanies(announce: List<OrganisationAnnouncements>) {
        this.bestCompaniesList = announce
        notifyDataSetChanged()
    }

    inner class OrganisationAnnouncementsViewHolder(private val vb: ItemCompaniesAnnouncementsRvBinding) :
        RecyclerView.ViewHolder(vb.root) {


        fun bind(announcements: OrganisationAnnouncements) = with(vb) {
            if (layoutPosition != RecyclerView.NO_POSITION) {


            }

        }
    }

}
