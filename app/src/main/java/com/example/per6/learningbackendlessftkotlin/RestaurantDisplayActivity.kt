package com.example.per6.learningbackendlessftkotlin

import android.content.Intent
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
import com.backendless.persistence.DataQueryBuilder
import kotlinx.android.synthetic.main.activity_restaurant_display.*
import kotlinx.android.synthetic.main.restaurant_recyclerview_item.view.*

class RestaurantDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_display)

        //get looged in owner id
        val ownerId = Backendless.UserService.CurrentUser().userId

        addRestaurantFAB.setOnClickListener {
            val intent = Intent(this, NewRestaurantActivity::class.java)
            startActivity(intent)
        }

        //recyclerview
        val restaurantAdapter = RestaurantAdapter(arrayListOf())
        restaurantsRecyclerView.adapter = restaurantAdapter

        //backendless data retrieval
        val dataQuery = DataQueryBuilder.create().apply {
            setWhereClause("ownerId = '${ownerId}'")
        }
        Backendless.Data.of(Restaurant::class.java).find(dataQuery, object : AsyncCallback<List<Restaurant>> {

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