package com.example.per6.learningbackendlessftkotlin

/**
 * Created by per6 on 2/22/18.
 */
data class Restaurant(var restaurantName: String = "", var genre : String = "", var address : String = "", var priceRating : Double = 0.0, var foodRating : Double = 0.0) {

    var objectId : String? = null
    var ownerId : String? = null
}