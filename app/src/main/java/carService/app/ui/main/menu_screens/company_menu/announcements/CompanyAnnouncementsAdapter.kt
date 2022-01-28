package carService.app.ui.main.menu_screens.company_menu.announcements

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carService.app.R
import carService.app.data.model.organization.announcements.OrganisationAnnouncements
import carService.app.databinding.ItemCompanyAnnouncementsRvBinding
import carService.app.utils.AppImageView
import carService.app.utils.ItemTouchHelperAdapter
import carService.app.utils.ItemTouchHelperViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class CompanyAnnouncementsAdapter(
    val imageLoader: AppImageView
) :
    RecyclerView.Adapter<CompanyAnnouncementsAdapter.CompanyAnnouncementsViewHolder>(),
    ItemTouchHelperAdapter {

    private var allAnnounceAdapterList: MutableList<OrganisationAnnouncements> = arrayListOf()
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val fireStore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CompanyAnnouncementsViewHolder(
        ItemCompanyAnnouncementsRvBinding.inflate(
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

    @SuppressLint("NotifyDataSetChanged")
    fun setAllServices(announce: MutableList<OrganisationAnnouncements>) {
        this.allAnnounceAdapterList = announce
        notifyDataSetChanged()
    }

    inner class CompanyAnnouncementsViewHolder(
        private val vb: ItemCompanyAnnouncementsRvBinding
    ) : RecyclerView.ViewHolder(vb.root),
        ItemTouchHelperViewHolder {


        fun bind(data: OrganisationAnnouncements) = with(vb) {
            vb.itemServiceTheme.text = data.serviceName ?: ""
            vb.itemServicePrice.text = data.price.toString()
            vb.itemServiceDate.text = data.data
            vb.itemServiceId.text = data.id.toString()
            vb.itemServiceOverview.text = data.serviceOverview

            val imagePath =
                "images/organisation/organisationAnnouncement/${data.uid}/${data.fileName}"
            FirebaseStorage.getInstance().reference
                .child(imagePath)
                .downloadUrl
                .addOnSuccessListener {
                    imageLoader.useCoilToLoadPhoto(
                        imageLink = it.toString(),
                        container = itemServiceImage,
                        imageView = itemServiceImage
                    )
                }
        }

        override fun onItemSelected() {
            itemView.setBackgroundResource(R.drawable.item_personal_service_requests_recycler_remove_background)
        }

        override fun onItemClear() {
            itemView.setBackgroundResource(R.drawable.item_near_companies_recycler_background)
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {}

    override fun onItemDismiss(position: Int) {
        val updates = hashMapOf<String, Any>(
            "orgAnnouncements" to FieldValue.arrayRemove(allAnnounceAdapterList[position])
        )

        val docRef = fireStore.collection("organisationAnnouncement")
            .document(auth.currentUser?.uid.toString())
        docRef.update(updates)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    allAnnounceAdapterList.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    return@addOnCompleteListener
                }
            }
    }

}

