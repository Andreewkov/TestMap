package kov.ru.testmap.ui.view.fragment


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import kov.ru.testmap.App

import kov.ru.testmap.R
import kov.ru.testmap.model.City
import kov.ru.testmap.ui.presenter.MapPresenter
import kov.ru.testmap.view.Maps
import javax.inject.Inject

class MapFragment : Fragment(), Maps.View {

    @Inject
    lateinit var presenter: MapPresenter
    private var googleMap: GoogleMap? = null
    private var markers: ArrayList<Marker> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.getInstance().injector.injectMapFragment(this)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            MapsInitializer.initialize(this.activity)
            googleMap = it
            googleMap?.uiSettings?.isZoomControlsEnabled = true


            googleMap?.setOnMarkerClickListener {
                it.showInfoWindow()
                if (it.tag != null && it.snippet != "")
                    presenter.markerClicked(it.tag.toString())
                return@setOnMarkerClickListener true
            }
            presenter.attachView(this)
        }
    }

    override fun showMarkers(cities: ArrayList<City>) {

        for (i: Int in 0 until cities.size) {
            val latlng = LatLng(cities[i].latitude, cities[i].longitude)
            val markerOptions = MarkerOptions()
                .position(latlng)
                .title("${cities[i].city}, ${cities[i].country}")
            if (cities[i].weather != "")
                markerOptions.snippet("${cities[i].weather}, ${cities[i].description}")

            activity?.runOnUiThread {
                val marker = googleMap?.addMarker(markerOptions)
                marker!!.tag = cities[i].city
                markers.add(marker)
                if (i == cities.size - 1)
                    camera(latlng, 3f)
            }
        }
    }

    override fun clearMarkers() {
        activity?.runOnUiThread {
            googleMap?.clear()
            markers.clear()
        }
    }

    override fun showWeather(city: City) {
        activity?.runOnUiThread {
                for (marker: Marker in markers) {
                    if (marker.tag != null && marker.tag!! == city.city) {
                        marker.snippet = "${city.weather}, ${city.description}"
                        if (marker.isInfoWindowShown)
                            marker.showInfoWindow()
                    }
                }
        }
    }

    override fun camera(latlng: LatLng, zoom: Float) {
        val cameraPosition =
            CameraPosition.builder().target(latlng).zoom(zoom).bearing(0f).tilt(4f).build()

        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
        presenter.detachView()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
    }
}
