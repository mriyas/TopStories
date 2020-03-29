package com.wisilica.imsafe.model

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.riyas.topstories.BR
import com.riyas.topstories.model.AppConstants
import com.wisilica.imsafe.data.local.SharedPreferencesUtility

class AppThemeMode(val ctx:Context): BaseObservable() {
    var mPref:SharedPreferencesUtility?=null

    @Bindable
    var themeMode:Int?=AppConstants.AppTheme.DAY_THEME
        get() = field
        set(value) {
            if(value!=field) {
                field = value
               // mPref?.setStringPrefValue(SharedPreferencesUtility.Constants.APP_URL, value)
                notifyPropertyChanged(BR.themeMode)
            }
        }

    init {
        mPref= SharedPreferencesUtility(ctx)
        themeMode = mPref?.getIntegerPrefValue(SharedPreferencesUtility.Constants.APP_THEME)
        if(themeMode==-1){
            themeMode=AppConstants.AppTheme.DAY_THEME
            save()
        }
    }
    fun save(): Boolean{

        mPref?.setIntegerPrefValue(SharedPreferencesUtility.Constants.APP_THEME, themeMode!!)

        return true
    }
}