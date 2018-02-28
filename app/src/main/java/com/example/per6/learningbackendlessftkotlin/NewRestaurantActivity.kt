package com.example.per6.learningbackendlessftkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import kotlinx.android.synthetic.main.activity_new_restaurant.*

class NewRestaurantActivity : AppCompatActivity() {

    private lateinit var restaurant : Restaurant

    companion object {
        const val GET_OBJECT_TO_UPDATE = "get object to update"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_restaurant)

        //info from intent
        restaurant = Restaurant()
        intent.getParcelableExtra<Restaurant>(GET_OBJECT_TO_UPDATE)?.let {
            restaurant = it
        }

        //widgets
        newResButton.setOnClickListener{
            it.isEnabled = false

            //create widgets
            restaurant.apply {
                restaurantName = newResName.text.toString()
                genre = newResGenre.text.toString()
                address = newResAddress.text.toString()
                foodRating = newResFoodRating.text.toString().toDouble()
                priceRating = newResPriceRating.text.toString().toDouble()
            }

            //try to save to backendless
            Backendless.Data.of(Restaurant::class.java).save(restaurant, object : AsyncCallback<Restaurant> {

                override fun handleFault(fault: BackendlessFault?) {
                    Toast.makeText(this@NewRestaurantActivity, fault?.message, Toast.LENGTH_SHORT).show()
                    it.isEnabled = true
                }

                override fun handleResponse(response: Restaurant?) {
                    Toast.makeText(this@NewRestaurantActivity, "Saved", Toast.LENGTH_SHORT).show()
                    setResult(RestaurantAdapter.EDIT_OBJECT_REQUEST_CODE)
                    finish()
                }

            })
        }

        with(restaurant) {
            newResName.setText(restaurantName)
            newResGenre.setText(genre)
            newResAddress.setText(address)
            newResFoodRating.setText(foodRating.toString())
            newResPriceRating.setText(priceRating.toString())
        }
    }
}
