package com.bitcode.sep_24_webservicesdemo_day1

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class WebThread : Thread() {

    override fun run() {
        super.run()

        val url = URL("https://api.slingacademy.com/v1/sample-data/photos?offset=5&limit=20")
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

        var responseObject = JSONObject(buffer.toString())
        var successValue = responseObject.getBoolean("success")
        var totalPhotos = responseObject.getInt("total_photos")
        var message = responseObject.getString("message")
        var offset = responseObject.getInt("offset")
        var limit = responseObject.getInt("limit")

        var photos : ArrayList<Photo> = ArrayList<Photo>()

        var photosArray = responseObject.getJSONArray("photos")

        for (i in 0..<photosArray.length()-1){
            val eachPhotoObject = photosArray.getJSONObject(i)
            var eachPhotoUrl = eachPhotoObject.getString("url")
            var eachPhotoTitle = eachPhotoObject.getString("title")
            var eachPhotoUser = eachPhotoObject.getInt("user")
            var eachPhotoDescription = eachPhotoObject.getString("description")
            var eachPhotoId = eachPhotoObject.getInt("id")

            val newPhotoObject = Photo(eachPhotoUrl,
                                            eachPhotoTitle,
                                            eachPhotoUser,
                                            eachPhotoDescription,
                                            eachPhotoId)
            photos.add(newPhotoObject)
        }
    }
}