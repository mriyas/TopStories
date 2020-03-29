package com.riyas.topstories.model


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.riyas.topstories.BR

data class SignInErrors(var userName : String?) : BaseObservable() {
    var userNameError : String? = null
        @Bindable
        get() = field

        set(value) {
            if(field != value) {
                field = value
                notifyPropertyChanged(BR.userNameError)
            }
        }
    var userPasswordError : String? = null
        @Bindable
        get() = field

        set(value) {
            if(field != value) {
                field = value
                notifyPropertyChanged(BR.userPasswordError)
            }
        }

    var uiUpdate : Boolean? = null
        @Bindable
        get() = field
        set(value) {
            if(field != value) {
                field = value
                notifyPropertyChanged(BR.uiUpdate)
            }
        }
    var disableState : Boolean? = true
        @Bindable
        get() = field
        set(value) {
            if(field != value) {
                field = value
                notifyPropertyChanged(BR.disableState)
            }
        }
    init {
        userNameError = userName
        userPasswordError = null
    }
}