package kov.ru.testmap.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kov.ru.testmap.R
import kov.ru.testmap.model.City
import kov.ru.testmap.model.Weather
import kov.ru.testmap.model.repository.SearchRepository
import kov.ru.testmap.model.repository.WeatherRepository
import kov.ru.testmap.ui.presenter.SearchCallback
import kov.ru.testmap.ui.presenter.WeatherCallback
import kov.ru.testmap.view.Main

class MainActivity : AppCompatActivity(), Main.View {

    private val MASERATI_PASSAGE_POSITION_X = 55.7577086
    private val MASERATI_PASSAGE_POSITION_Y = 37.5802237
    private val MASERATI_LUXURY_POSITION_X = 55.7492695
    private val MASERATI_LUXURY_POSITION_Y = 37.3155004

    private var maseratiPassagePosition: LatLng? = null
    private var maseratiLuxuryPosition: LatLng? = null

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherRepository.getInstance().getWeather("294021", object : WeatherCallback {
            override fun returnResult(weather: Weather) {
                val a = 0
            }

        })




        //WeatherRepository().loadWeather("Moscow")
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            MapsInitializer.initialize(this)
            googleMap = it
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            maseratiPassagePosition = LatLng(MASERATI_PASSAGE_POSITION_X, MASERATI_PASSAGE_POSITION_Y)
            maseratiLuxuryPosition = LatLng(MASERATI_LUXURY_POSITION_X, MASERATI_LUXURY_POSITION_Y)

            googleMap?.addMarker(
                MarkerOptions()
                    .position(maseratiPassagePosition!!)
            )!!.showInfoWindow()
            googleMap?.addMarker(
                MarkerOptions()
                    .position(maseratiLuxuryPosition!!)
            )

            val cameraPosition =
                CameraPosition.builder().target(LatLng(55.7494446, 37.4296001)).zoom(9f).bearing(0f).tilt(45f).build()
            googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

            googleMap?.setOnMarkerClickListener {
                it.showInfoWindow()
                return@setOnMarkerClickListener true
            }
        }

    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    public override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}
