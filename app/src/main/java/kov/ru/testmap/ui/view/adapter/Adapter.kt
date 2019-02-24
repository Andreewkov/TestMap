package kov.ru.testmap.ui.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kov.ru.testmap.R
import kov.ru.testmap.model.City

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var list: ArrayList<City>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position : Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(list!![position])
    }

    fun showElements(list: ArrayList<City>?) {
        this.list = list
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cityName: TextView = view.findViewById(R.id.cityName)
        var city: City? = null

        fun onBind(city: City) {
            cityName.text = "${city.city}, ${city.country}"
            this.city = city
        }
    }
}