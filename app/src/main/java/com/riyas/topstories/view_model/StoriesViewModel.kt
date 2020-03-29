package com.riyas.topstories.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.riyas.topstories.model.AppConstants
import com.riyas.topstories.model.StoryResponse
import com.riyas.topstories.model.TopStoryIdResponse
import kotlinx.coroutines.*
import java.lang.IndexOutOfBoundsException

class StoriesViewModel(application: Application) : BaseViewModel(application) {
    val TAG = javaClass.simpleName
    var mTopStoryIds = MutableLiveData<TopStoryIdResponse>()
    var mStories = HashMap<String, StoryResponse?>()
    var mLastLoadedIndex = 0
    var mDatatState = MutableLiveData<Int>()
    var isVisible=MutableLiveData<Boolean>()

    init {
        cloudDataManager = getService()
        isVisible.value=true
    }

    fun getTotalItemCount(): Int {
        val size = mTopStoryIds.value?.data?.size as Int
        return size
    }

    fun fetchTopStoryIds() {
        if(!checkConnection(getApplication())){
            mDatatState.postValue(AppConstants.NETWORK_FAILURE)
            return
        }
        cloudDataManager?.getToStoryIds(mTopStoryIds)
        mDatatState.postValue(AppConstants.STORIES_LOADING)
    }

    fun getList(): ArrayList<StoryResponse?>? {
        return mStories.values?.let { ArrayList(it) }

    }

    fun loadInitialData(isClear:Boolean=false){
        if(isClear) {
            mStories?.clear()
            mDatatState.postValue(AppConstants.STORIES_LOADED)
        }
        loadStories(0,20)
    }

    fun setVisibility(){
        isVisible.postValue( mStories==null||mStories.size!!<=0)
    }


    fun loadStories(index: Int, limit: Int) {
        if(mDatatState.value==AppConstants.STORIES_LOADING){
            return
        }
        if (mTopStoryIds?.value?.data?.size == 0) {
            return
        }
        if(!checkConnection(getApplication())){
            mDatatState.postValue(AppConstants.NETWORK_FAILURE)
            return
        }
        mDatatState.postValue(AppConstants.STORIES_LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList<StoryResponse?>()
            val listIds = ArrayList<String>()
            for (x in index until index+limit) {
                //      var i = x
                val size=mTopStoryIds?.value?.data?.size
                if(x>=size!!){
                    break
                }
                var storyId:String?=null
                try {
                    storyId = mTopStoryIds?.value?.data?.get(x)
                }catch (ex:IndexOutOfBoundsException){
                    break
                }
                if (!storyId.isNullOrEmpty()) {

                    val story = cloudDataManager?.getStory1(storyId)
                    list.add(story)
                    listIds.add(storyId)
                }
                mLastLoadedIndex++

            }
            Log.v(TAG, "Size=" + list.size)
            if (mStories == null) {
                mStories = HashMap<String, StoryResponse?>()
            }

            val map=HashMap<String,StoryResponse?>()
            for (x in 0 until listIds.size) {
                val storyId = listIds.get(x)
                val story = list.get(x)
                Log.d(TAG, "loadStory() x=$x, storyId=$storyId, title=${story?.title} ")
                map.put(storyId, story)
                mStories.put(storyId, story)
            }

            Log.i(TAG, "loadStories() total data received="+map.size+":"+mStories.size)

        //    mStories.postValue(mStories.value)
            mDatatState.postValue(AppConstants.STORIES_LOADED)

            setVisibility()

        }

    }


}