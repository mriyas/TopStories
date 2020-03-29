package com.riyas.topstories.view_model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.riyas.topstories.data.cloud.CloudDataManager
import com.riyas.topstories.model.AppState

open class BaseViewModel (application: Application):AndroidViewModel(application){
    var mApplication: Application
    val mState = MutableLiveData<AppState>()
    init {
        this.mApplication=application
    }
    var cloudDataManager: CloudDataManager? = null
    fun getService(): CloudDataManager?{
        if (null == cloudDataManager)
            cloudDataManager = CloudDataManager(getApplication())
        return cloudDataManager
    }
    fun setState(status: Int) {
        var value=mState.value
        if(value==null){
            value=  AppState(null)
        }
        val state=value
        state.state=status
        mState.value = state
    }

    fun checkConnection(ctx: Context): Boolean {
        try {
            if (ctx != null) {
                val connMgr = ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connMgr.activeNetworkInfo
                return if (networkInfo != null && networkInfo.isConnected) {
                    true
                } else {
                    false
                }

            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }

    }
}