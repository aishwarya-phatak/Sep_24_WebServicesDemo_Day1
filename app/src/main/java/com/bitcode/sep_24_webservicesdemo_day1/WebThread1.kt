package com.bitcode.sep_24_webservicesdemo_day1

import android.util.Log
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class WebThread1 : Thread() {
    override fun run() {
        super.run()

        val url = URL("https://fakestoreapi.com/products")
        var httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.connect()
        Log.e("tag", "----------------------------------------")
        Log.e("tag", httpURLConnection.requestMethod)
        Log.e("tag", httpURLConnection.responseMessage)
        Log.e("tag", httpURLConnection.responseCode.toString())
        Log.e("tag", "----------------------------------------")

        var inStream = httpURLConnection.inputStream
        Log.e("tag", "----------------------------------------")
        Log.e("tag", inStream.toString())

        var buffer = StringBuffer()
        var data = ByteArray(1024 * 1)
        var count = 0

        count = inStream.read(data)
        while (count != -1){
            buffer.append(String(data, 0 , count))
            count = inStream.read(data)
        }

        inStream.close()
        Log.e("tag", "----------------------------------------")
        Log.e("tag",buffer.toString())

        var products = ArrayList<Product>()
        val rootAPIResponse = JSONArray(buffer.toString())
        for (i in 0..<rootAPIResponse.length()){
            val eachProduct = rootAPIResponse.getJSONObject(i)
            val eachProductId = eachProduct.getInt("id")
            val eachProductTitle = eachProduct.getString("title")
            val eachProductPrice = eachProduct.getDouble("price")
            val eachProductDescription = eachProduct.getString("description")
            val eachProductCategory = eachProduct.getString("category")
            val eachProductImage = eachProduct.getString("image")

            val eachProductRating = eachProduct.getJSONObject("rating")
            val eachRate = eachProductRating.getDouble("rate")
            val eachCount = eachProductRating.getInt("count")

            val newRating =  Rating(eachRate,
                                    eachCount)
            val newProduct = Product(eachProductId,
                eachProductTitle,
                eachProductPrice,
                eachProductDescription,
                eachProductCategory,
                eachProductImage,
                newRating
               )

            products.add(newProduct)
        }
    }
}