package kov.ru.testmap.ui.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kov.ru.testmap.App
import kov.ru.testmap.R
import kov.ru.testmap.model.City
import kov.ru.testmap.model.db.DBRepository
import kov.ru.testmap.ui.presenter.MainPresenter
import kov.ru.testmap.ui.view.TabAdapter
import kov.ru.testmap.view.Main
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Main.View, DialogCallback {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.getInstance().injector.injectMainActivity(this)
        addStartedCities()
        presenter.attachView(this)

        tabLayout.addTab(tabLayout.newTab().setText("Карта погоды"))
        tabLayout.addTab(tabLayout.newTab().setText("Города"))

        val tabAdapter = TabAdapter(fragmentManager)
        viewPager.adapter = tabAdapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun addStartedCities() {
        if (getPreferences(Context.MODE_PRIVATE).getBoolean("first", true)) {
            DBRepository.getInstance().add(City("Moscow", "Russia", 55.752, 37.619, "", ""), null)
            DBRepository.getInstance().add(City("Saint Petersburg", "Russia", 59.939, 30.315, "", ""), null)

            val editor: SharedPreferences.Editor = getPreferences(Context.MODE_PRIVATE).edit()
            editor.putBoolean("first", false)
            editor.apply()
        }
    }

    override fun load(location: String) {
        presenter.addLocation(location)
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}
interface DialogCallback {
    fun load(location: String)
}