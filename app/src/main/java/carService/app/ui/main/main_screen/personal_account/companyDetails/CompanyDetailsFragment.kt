package carService.app.ui.main.main_screen.personal_account.companyDetails

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.R
import carService.app.data.model.organization.OrganisationData
import carService.app.data.model.organization.OrganisationServiceList
import carService.app.databinding.CompanyDetailsFragmentBinding
import carService.app.ui.WorkaroundMapFragment
import carService.app.ui.main.main_screen.company_account.CompanyAllServicesAdapter
import carService.app.ui.registration.REQUEST_CODE_FOR_MAPS
import carService.app.utils.AppImageView
import carService.app.utils.showsnackBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.io.IOException

class CompanyDetailsFragment : Fragment(R.layout.company_details_fragment) {

    private lateinit var thisMap: GoogleMap

    companion object {
        const val TAG = "CompanyDetailsFragment"
        fun newInstance() = CompanyDetailsFragment()
    }

    private val binding: CompanyDetailsFragmentBinding by viewBinding()
    private val viewModel: CompanyDetailsViewModel by lazy {
        ViewModelProvider(this)[CompanyDetailsViewModel::class.java]
    }
    private lateinit var navBar: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navBar = requireActivity().findViewById(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFullOrganisationMockData()

        viewModel.getLiveData().observe(viewLifecycleOwner, { checkGPSPermission(it) })
    }


    private fun renderData(appState: CompanyDetailsAppState) {
        when (appState) {
            is CompanyDetailsAppState.Success -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE

                binding.companyLogoImageView.visibility = View.VISIBLE
                binding.companyNameTextview.visibility = View.VISIBLE
                binding.companyServicesRv.visibility = View.VISIBLE
                binding.companyRating.visibility = View.VISIBLE
                binding.companyOverview.visibility = View.VISIBLE
                binding.companyTelTextview.visibility = View.VISIBLE
                binding.companyWorkTime.visibility = View.VISIBLE
                binding.companyEmailTextview.visibility = View.VISIBLE
                navBar.visibility = View.VISIBLE

                setAllOrgData(appState.organisationData)
                setOrgServicesData(appState.organisationServiceData)

            }
            is CompanyDetailsAppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE

                navBar.visibility = View.GONE
                binding.companyLogoImageView.visibility = View.GONE
                binding.companyNameTextview.visibility = View.GONE
                binding.companyServicesRv.visibility = View.GONE
                binding.companyRating.visibility = View.GONE
                binding.companyOverview.visibility = View.GONE
                binding.companyTelTextview.visibility = View.GONE
                binding.companyInstagramTextview.visibility = View.GONE
                binding.companyFacebookTextview.visibility = View.GONE
                binding.companyVkTextview.visibility = View.GONE
                binding.companyYoutubeTextview.visibility = View.GONE
                binding.companyWorkTime.visibility = View.GONE
                binding.companyEmailTextview.visibility = View.GONE


            }
            is CompanyDetailsAppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.visibility = View.GONE
                binding.mainFragmentView.showsnackBar("Error")
            }
        }
    }

    private fun setAllOrgData(appState: List<OrganisationData>) {

        if (appState[0].telephoneNumbers?.isNotEmpty() == true) {
            for (element in appState[0].telephoneNumbers!!) binding.companyTelTextview
                .append("${element.tel}\n")
        }

        binding.companyNameTextview.text = appState[0].name
        binding.companyRating.text = appState[0].rating.toString()
        binding.companyOverview.text = appState[0].overview
        binding.companyWorkTime.text =
            (getString(R.string.monday) + " ${appState[0].workingTime?.workingTimeMonday}\n" +
                    getString(R.string.tuesday) + " ${appState[0].workingTime?.workingTimeTuesday}\n" +
                    getString(R.string.wednesday) + " ${appState[0].workingTime?.workingTimeWednesday}\n" +
                    getString(R.string.thursday) + " ${appState[0].workingTime?.workingTimeThursday}\n" +
                    getString(R.string.friday) + " ${appState[0].workingTime?.workingTimeFriday}\n" +
                    getString(R.string.saturday) + " ${appState[0].workingTime?.workingTimeSaturday}\n" +
                    getString(R.string.sunday) + " ${appState[0].workingTime?.workingTimeSunday}\n")

        binding.companyEmailTextview.text = appState[0].email

        appState[0].mainOrganisationPhoto?.let {
            AppImageView().useCoilToLoadPhoto(
                imageLink = it,
                container = binding.companyLogoImageView,
                imageView = binding.companyLogoImageView
            )
        }

        showSocial(appState)
    }

    private fun showSocial(appState: List<OrganisationData>) {
        if (appState[0].social?.socialInstagram?.name?.isNotBlank() == true) {
            binding.companyInstagramTextview.visibility = View.VISIBLE
        }

        if (appState[0].social?.socialFacebook?.name?.isNotBlank() == true) {
            binding.companyFacebookTextview.visibility = View.VISIBLE
        }

        if (appState[0].social?.socialVk?.name?.isNotBlank() == true) {
            binding.companyVkTextview.visibility = View.VISIBLE
        }

        if (appState[0].social?.socialYoutube?.name?.isNotBlank() == true) {
            binding.companyYoutubeTextview.visibility = View.VISIBLE
        }
    }

    private fun setOrgServicesData(appState: List<OrganisationServiceList>) {
        val companyAllData: RecyclerView = binding.companyServicesRv
        companyAllData.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val companyAllDataAdapter = CompanyAllServicesAdapter(AppImageView())
        companyAllData.adapter = companyAllDataAdapter

        companyAllDataAdapter.setAllServices(appState)

    }

    private fun activateMyLocation(googleMap: GoogleMap) {
        context?.let {
            val isPermissionGranted =
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                        PackageManager.PERMISSION_GRANTED
            googleMap.isMyLocationEnabled = isPermissionGranted
            googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted
        }
    }

    private fun checkGPSPermission(appState: CompanyDetailsAppState) {
        when (appState) {
            is CompanyDetailsAppState.Success -> {
                initSearchByCompanyLocation(appState.organisationData)
            }

            else -> {}
        }
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {

                    getMapData() // Если уже получено разрешение, то получаем контакты далее

                }
                // Метод для нас, чтобы знали когда необходимы пояснения показывать перед запросом:
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Необходим доступ к GPS")
                        .setMessage(
                            "Внимание! Для просмотра данных на карте необходимо разрешение на" +
                                    "использование Вашего местоположения"
                        )
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestGPSPermission()
                        }
                        .setNegativeButton("Спасибо, не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {

                    requestGPSPermission() // Если разрешения нет и объяснение отображать не нужно, то
                    // запрашиваем разрешение
                }
            }
        }
    }

    private fun getMapData() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as WorkaroundMapFragment?
        /** Чтобы норм было в Scroll View! */
        mapFragment?.getMapAsync { googleMap ->
            thisMap = googleMap
            thisMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            thisMap.uiSettings.isZoomControlsEnabled = true

            activateMyLocation(thisMap)
            val mScrollView =
                binding.scrollView
            (childFragmentManager.findFragmentById(R.id.map) as WorkaroundMapFragment)
                .setListener(object : WorkaroundMapFragment.OnTouchListener {

                    override fun onTouch() {
                        mScrollView.requestDisallowInterceptTouchEvent(true)
                    }
                })
        }
    }

    private fun requestGPSPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_FOR_MAPS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_FOR_MAPS -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    getMapData()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к GPS")
                            .setMessage(
                                "Внимание! Для просмотра данных на карте необходимо разрешение на " +
                                        "использование Вашего местоположения"
                            )
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }

    private fun initSearchByCompanyLocation(appState: List<OrganisationData>) {

        val geoCoder = Geocoder(context)
        val place =
            appState[0].location?.latitude.toString() + " " + appState[0].location?.longitude.toString()
        Thread {
            try {
                val addresses = geoCoder.getFromLocationName(place, 1)
                if (addresses.size > 0) {
                    lifecycleScope.launch {
                        goToAddress(addresses, appState)
                    }

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        appState: List<OrganisationData>
    ) {
        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )

        val name = appState[0].name.toString()

        try {
            val drawable = binding.companyLogoImageView.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            view?.post {
                setMarker(location, name, bitmap)
                thisMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        location,
                        14f
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setMarker(
        location: LatLng,
        name: String,
        bitmap: Bitmap
    ): Marker? {
        return thisMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(name)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(bitmap)
                )
        )
    }

}