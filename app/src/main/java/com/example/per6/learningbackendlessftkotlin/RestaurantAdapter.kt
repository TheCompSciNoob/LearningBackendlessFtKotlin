package com.example.per6.learningbackendlessftkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.restaurant_recyclerview_item.view.*

/**
 * Created by per6 on 2/26/18.
 */
class RestaurantViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
}

class RestaurantAdapter(var restaurantList : List<Restaurant>) : RecyclerView.Adapter<RestaurantViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RestaurantViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val rootView = layoutInflater.inflate(R.layout.restaurant_recyclerview_item, parent, false)
        return RestaurantViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder?, position: Int) {
        val item = restaurantList[position]
        holder?.itemView?.apply {
            restaurantName.text = item.restaurantName
            genre.text = item.genre
            address.text = item.address
            foodRating.rating = item.foodRating.toFloat()
            priceRating.rating = item.priceRating.toFloat()
        }
    }
}