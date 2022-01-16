package carService.app.ui.registration

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.FragmentMapsBinding
import carService.app.utils.navigate
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.util.*

const val REQUEST_CODE_FOR_MAPS = 723

class MapsFragment(override val layoutId: Int = R.layout.fragment_maps) :
    BaseFragment<FragmentMapsBinding>() {

    companion object {
        const val TAG = "MapsFragment"
        fun newInstance() = MapsFragment()
    }

    private lateinit var viewModel: MapsViewModel
    private lateinit var thisMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        thisMap = googleMap
        activateMyLocation(thisMap) // Сетим появление штатной кнопки для показа моего места

    }

    private lateinit var client: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client = activity?.let { it1 -> LocationServices.getFusedLocationProviderClient(it1) }!!
        checkGPSPermission() // Запрашиваем все разрешения

        binding.saveLocationButton.setOnClickListener {
            navigate(R.id.registrationStep4LocationFragment)
        }
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

    private fun checkGPSPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {

                    getMapData() // Если уже получено разрешение, то получаем контакты далее

                    initSearchByAddress() // Это логика поиска при вводе места вручную

                    getMyCurrentLocation()
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

        // находим нужный нам фрагмент, приводим его к типу SupportMapFragment и подготавливаем карту
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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
                // Проверяем, дано ли пользователем разрешение по нашему запросу
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    getMapData()
                } else {
                    // Поясните пользователю, что экран останется пустым, потому что доступ к контактам не предоставлен
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

    private fun initSearchByAddress() {
        binding.doSearchAddressButton.setOnClickListener {
            val geoCoder = Geocoder(it.context, Locale.getDefault())
            val place = binding.searchAddressInputField.text.toString()
            Thread {
                try {
                    val addresses = geoCoder.getFromLocationName(place, 1)
                    if (addresses.size > 0) {
                        goToAddress(addresses, it)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        view: View
    ) {

        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )

        val geoCoder = Geocoder(context, Locale.getDefault())
        val myPlaceByLocation: List<Address> =
            geoCoder.getFromLocation(location.latitude, location.longitude, 1)
        val myAddress = myPlaceByLocation[0].getAddressLine(0)

        val bundle = Bundle()
        bundle.putString("Location by Search", myAddress)
        binding.saveLocationButton.setOnClickListener {
            navigate(R.id.registrationStep4LocationFragment, bundle)
        }
        view.post {
            setMarker(location, myAddress)
            thisMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    10f
                )
            )
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String
    ): Marker? {
        return thisMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.defaultMarker())
        )
    }

    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {

        val task: Task<Location> = client.lastLocation
        task.addOnSuccessListener { location ->
            if (location !== null) {
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync {
                    saveMyLocation(location)
                }
            }
        }
    }

    private fun saveMyLocation(location: Location) {
        val loc = LatLng(
            location.latitude,
            location.longitude
        )
        val options: MarkerOptions = MarkerOptions().position(loc)
            .title("I am here bro!")
        thisMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10f))
        thisMap.addMarker(options)

        val geoCoder = Geocoder(context, Locale.getDefault())
        val myPlaceByLocation: List<Address> =
            geoCoder.getFromLocation(location.latitude, location.longitude, 1)
        val myAddress = myPlaceByLocation[0].getAddressLine(0)
        val bundle = Bundle()
        bundle.putString("Location by Search", myAddress)
        binding.saveLocationButton.setOnClickListener {
            navigate(R.id.registrationStep4LocationFragment, bundle)
        }
    }

}