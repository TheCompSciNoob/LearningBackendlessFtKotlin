package com.example.per6.learningbackendlessftkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_restaurant.*

class NewRestaurantActivity : AppCompatActivity() {

    companion object {
        const val GET_OBJECT_TO_UPDATE = "get object to update"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_restaurant)

        //info from intent

        //widgets
        newResButton.setOnClickListener{
            it.isEnabled = false

            //create widgets
            val restaurant = Restaurant().apply {
                restaurantName = newResName.text.toString()
                genre = newResGenre.text.toString()
                address = newResAddress.text.toString()
                foodRating = newResFoodRating.text.toString().toDouble()
                priceRating = newResPriceRating.text.toString().toDouble()
            }

            //try to save to backendless
        }
    }
}
