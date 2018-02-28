package com.example.per6.learningbackendlessftkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import kotlinx.android.synthetic.main.activity_restaurant_display.*

class RestaurantDisplayActivity : AppCompatActivity() {

    private lateinit var restaurantAdapter: RestaurantAdapter
    private val ownerId : String by lazy {
        Backendless.UserService.CurrentUser().userId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_display)

        addRestaurantFAB.setOnClickListener {
            val intent = Intent(this, NewRestaurantActivity::class.java)
            startActivity(intent)
        }

        //recyclerview
        restaurantAdapter = RestaurantAdapter(arrayListOf())
        restaurantsRecyclerView.adapter = restaurantAdapter

        retreieveRestaurants()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode){
            RestaurantAdapter.EDIT_OBJECT_REQUEST_CODE ->
                retreieveRestaurants()
        }
    }

    private fun retreieveRestaurants() {
        //backendless data retrieval
        val dataQuery = DataQueryBuilder.create().apply {
            whereClause = "ownerId = '$ownerId'"
        }
        Backendless.Data.of(Restaurant::class.java).find(dataQuery, object : AsyncCallback<List<Restaurant>> {

            override fun handleFault(fault: BackendlessFault?) {
                Toast.makeText(this@RestaurantDisplayActivity, "data retrieval failed", Toast.LENGTH_SHORT).show()
            }

            override fun handleResponse(response: List<Restaurant>?) {
                Log.d("response handled: ",  response?.toString())
                response?.let {
                    restaurantAdapter.restaurantList = it.toMutableList()
                }
                restaurantAdapter.notifyDataSetChanged()
            }

        })
    }
}