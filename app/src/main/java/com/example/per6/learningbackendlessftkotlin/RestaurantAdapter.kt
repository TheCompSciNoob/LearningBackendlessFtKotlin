package com.example.per6.learningbackendlessftkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import kotlinx.android.synthetic.main.restaurant_recyclerview_item.view.*

/**
 * Created by per6 on 2/26/18.
 */
class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
}

class RestaurantAdapter(var restaurantList: MutableList<Restaurant>) : RecyclerView.Adapter<RestaurantViewHolder>() {

    companion object {
        val EDIT_OBJECT_REQUEST_CODE = 9999
    }

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

        //binding
        holder?.itemView?.apply {
            restaurantName.text = item.restaurantName
            genre.text = item.genre
            address.text = item.address
            foodRating.rating = item.foodRating.toFloat()
            priceRating.rating = item.priceRating.toFloat()

            setOnLongClickListener {
                //popup menu
                val popupMenu = PopupMenu(context, this)
                popupMenu.menuInflater.inflate(R.menu.recyclerview_popup, popupMenu.menu)
                //onclick
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit_res ->
                            (context as AppCompatActivity).startActivityForResult(Intent(context, NewRestaurantActivity::class.java).apply {
                                putExtra(NewRestaurantActivity.GET_OBJECT_TO_UPDATE, item)
                            }, EDIT_OBJECT_REQUEST_CODE)
                        R.id.delete_res -> Backendless.Data.of(Restaurant::class.java).remove(item, object : AsyncCallback<Long> {

                            override fun handleResponse(response: Long?) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                                restaurantList.remove(item)
                                notifyDataSetChanged()
                            }

                            override fun handleFault(fault: BackendlessFault?) {
                                Toast.makeText(context, fault?.message, Toast.LENGTH_SHORT).show()
                            }

                        })
                    }
                    return@setOnMenuItemClickListener true
                }
                popupMenu.show()
                return@setOnLongClickListener true
            }
        }
    }
}