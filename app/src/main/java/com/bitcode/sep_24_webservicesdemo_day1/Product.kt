package com.bitcode.sep_24_webservicesdemo_day1

data class Product(
    var id : Int,
    var title : String,
    var price : Double,
    var description : String,
    var category : String,
    var image : String,
    var rating : Rating
)

data class Rating(
    var rate : Double,
    var count : Int
)