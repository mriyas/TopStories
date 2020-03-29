package com.riyas.topstories.data.cloud

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.riyas.topstories.BuildConfig
import com.riyas.topstories.model.StoryResponse
import com.wisilica.retrfit.RetrofitClient
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CloudService {

    @GET("topstories.json")
    fun getTopStoryIds(): Observable<List<String>>

    @GET("item/{id}.json")
    fun getStory(@Path(value = "id", encoded = true) id: String?): Observable<StoryResponse>

    @GET("item/{id}.json")
    suspend fun getStory1(@Path(value = "id", encoded = true) id: String?): StoryResponse

    companion object Factory {

        fun create(context: Context): CloudService? {
            var url = BuildConfig.WEB_SERVICE_BASE_URL

            val retrofit = RetrofitClient.getRetrofitClient(url)
            return retrofit!!.create(CloudService::class.java)
        }
    }
}