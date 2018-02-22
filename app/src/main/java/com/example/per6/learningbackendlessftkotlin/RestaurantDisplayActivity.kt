package com.example.per6.learningbackendlessftkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import kotlinx.android.synthetic.main.activity_restaurant_display.*
import kotlinx.android.synthetic.main.restaurant_recyclerview_item.view.*

class RestaurantDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_display)

        //get looged in owner id
        val ownerId = intent.getStringExtra(LoginActivity.GET_LOGGED_IN_OWNER_ID)

        addRestaurantFAB.setOnClickListener {
            //TODO: start new activity to create restaurant
        }

        //recyclerview
        val restaurantAdapter = RestaurantAdapter(arrayListOf())
        restaurantsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RestaurantDisplayActivity)
            adapter = restaurantAdapter
        }

        //backendless data retrieval
        Backendless.Persistence.of(Restaurant::class.java).find(object : AsyncCallback<List<Restaurant>> {

            override fun handleFault(fault: BackendlessFault?) {
                Toast.makeText(this@RestaurantDisplayActivity, "data retrieval failed", Toast.LENGTH_SHORT).show()
            }

            override fun handleResponse(response: List<Restaurant>?) {
                restaurantAdapter.restaurantList = response!!
                restaurantAdapter.notifyDataSetChanged()
            }
        })
    }
}

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
            restaurantNameTextView.text = item.name
            priceRatingTextView.text = item.priceRating.toString()
            addressTextView.text = item.address
            foodRatingTextView.text = item.foodRating.toString()
        }
    }
}
