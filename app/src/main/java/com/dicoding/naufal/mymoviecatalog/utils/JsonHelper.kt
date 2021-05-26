package com.dicoding.naufal.mymoviecatalog.utils

import android.content.Context
import com.dicoding.naufal.mymoviecatalog.data.source.remote.response.ShowResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {
    private fun parsingFileToString(fileName: String): String?{
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        }catch (ex: IOException){
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): List<ShowResponse>{
        val list = ArrayList<ShowResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("ShowResponses.json").toString())
            val listArray = responseObject.getJSONArray("shows")
            for (i in 0 until listArray.length()){
                val show = listArray.getJSONObject(i)

                if (show.getBoolean("isMovie")){
                    val id = show.getString("id")
                    val title = show.getString("title")
                    val releaseDate = show.getString("releaseDate")
                    val writer = show.getString("writer")
                    val genre = show.getString("genre")
                    val sinopsys = show.getString("sinopsys")
                    val posterPath = show.getString("posterPath")
                    val scene1Path = show.getString("scene1Path")
                    val scene2Path = show.getString("scene2Path")
                    val scene3Path = show.getString("scene3Path")
                    val stars = show.getString("stars")

                    val showResponse = ShowResponse(id, title, releaseDate, writer, genre, sinopsys, posterPath, scene1Path, scene2Path, scene3Path, stars, true)
                    list.add(showResponse)
                }
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
        return list
    }

    fun loadSeries(): List<ShowResponse>{
        val list = ArrayList<ShowResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("ShowResponses.json").toString())
            val listArray = responseObject.getJSONArray("shows")
            for (i in 0 until listArray.length()){
                val show = listArray.getJSONObject(i)

                if (!show.getBoolean("isMovie")){
                    val id = show.getString("id")
                    val title = show.getString("title")
                    val releaseDate = show.getString("releaseDate")
                    val writer = show.getString("writer")
                    val genre = show.getString("genre")
                    val sinopsys = show.getString("sinopsys")
                    val posterPath = show.getString("posterPath")
                    val scene1Path = show.getString("scene1Path")
                    val scene2Path = show.getString("scene2Path")
                    val scene3Path = show.getString("scene3Path")
                    val stars = show.getString("stars")

                    val showResponse = ShowResponse(id, title, releaseDate, writer, genre, sinopsys, posterPath, scene1Path, scene2Path, scene3Path, stars, false)
                    list.add(showResponse)
                }
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
        return list
    }

    fun loadShowDetail(showId: String): ShowResponse{
        var showResponse: ShowResponse? = null
        try {
            val responseObject = JSONObject(parsingFileToString("ShowResponses.json").toString())
            val listArray = responseObject.getJSONArray("shows")
            for (i in 0 until listArray.length()){
                val show = listArray.getJSONObject(i)

                if (show.getString("id") == showId){
                    val id = show.getString("id")
                    val title = show.getString("title")
                    val releaseDate = show.getString("releaseDate")
                    val writer = show.getString("writer")
                    val genre = show.getString("genre")
                    val sinopsys = show.getString("sinopsys")
                    val posterPath = show.getString("posterPath")
                    val scene1Path = show.getString("scene1Path")
                    val scene2Path = show.getString("scene2Path")
                    val scene3Path = show.getString("scene3Path")
                    val stars = show.getString("stars")

                    showResponse = ShowResponse(id, title, releaseDate, writer, genre, sinopsys, posterPath, scene1Path, scene2Path, scene3Path, stars, false)
                }
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }
        return showResponse as ShowResponse
    }
}