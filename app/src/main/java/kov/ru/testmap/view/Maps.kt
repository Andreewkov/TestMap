package kov.ru.testmap.view

import com.google.android.gms.maps.model.LatLng
import kov.ru.testmap.model.City

interface Maps {
    interface View {
        fun showMarkers(cities: ArrayList<City>)
        fun clearMarkers()
        fun showWeather(city: City)
        fun camera(lng: LatLng, zoom: Float)
        fun showToast(text: String)
    }

    interface Presenter {
        var isAttached: Boolean
        fun attachView(view: Maps.View)
        fun detachView()
        fun markerClicked(location: String)
    }
}