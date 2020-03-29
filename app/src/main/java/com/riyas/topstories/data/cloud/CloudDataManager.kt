package com.riyas.topstories.data.cloud

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.riyas.topstories.model.ApiError
import com.riyas.topstories.model.AppConstants
import com.riyas.topstories.model.StoryResponse
import com.riyas.topstories.model.TopStoryIdResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CloudDataManager (var context: Context){
    val service = CloudService.create(context)

    val TAG=javaClass.simpleName
    fun getToStoryIds(data: MutableLiveData<TopStoryIdResponse>): MutableLiveData<TopStoryIdResponse> {
    //    var headerMap = dashboardCountRequest.getHeaderWithToken();
        service?.getTopStoryIds()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({result ->
                Log.e("response", ""+result)
                val topstory=TopStoryIdResponse(result)
                data.value=topstory
            }, {error->
                Log.e("error", error.message)
                val topstory=TopStoryIdResponse(null)
                val apiError =ApiError(AppConstants.API_ERROR,error.localizedMessage)
                topstory.error=apiError
                data.value = topstory
            })
        return data
    }

    fun getStory(id:String,mStories:MutableLiveData<HashMap<String, StoryResponse?>>): MutableLiveData<StoryResponse> {
        val data = MutableLiveData<StoryResponse>()
        //    var headerMap = dashboardCountRequest.getHeaderWithToken();
        service?.getStory(id)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({result ->
                Log.e("response", ""+result)
                data.value=result
                if (mStories.value == null) {
                    val data = HashMap<String, StoryResponse?>()
                    mStories.postValue(data)
                }
                if (result!=null) {
                    Log.d(TAG,"getStory() : storyId=$id, title=${result?.title} ")
                    mStories.value?.put(id, result)
                    mStories.postValue(mStories.value)
                }
            }, {error->
                Log.e("error", error.message)
                val storyResponse=StoryResponse()
                val apiError =ApiError(AppConstants.API_ERROR,error.localizedMessage)
                storyResponse.error=apiError
                data.value = storyResponse
            })
        return data
    }


    suspend fun getStory1(id:String): StoryResponse? {

        return service?.getStory1(id)
    }
}