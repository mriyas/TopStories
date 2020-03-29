package com.riyas.topstories.view_model

import android.app.Application
import com.riyas.topstories.R
import com.riyas.topstories.model.AppConstants
import com.riyas.topstories.model.SignInErrors
import com.riyas.topstories.model.UserDetails

class UserViewModel(application: Application) : BaseViewModel(application){


    fun doLogin(userDetails: UserDetails, siginError: SignInErrors){
        siginError.userNameError = isValidUserName(userDetails.username)
        siginError.userPasswordError = isValidPassword(userDetails.password)
        if (siginError.userNameError.isNullOrEmpty()
            && siginError.userPasswordError.isNullOrEmpty())
         {
            siginError.uiUpdate = true


        }
        setState(AppConstants.ACTION_LOGIN_CLICK)
    }
    fun onTextChanged(userDetails: UserDetails,text:CharSequence, siginError: SignInErrors,type:Int){
        when(type){
            1->{
                userDetails.username=text?.toString()
            }
            2->{
                userDetails.password=text?.toString()
            }
        }
        siginError.disableState = userDetails.username.isNullOrEmpty()||userDetails.password.isNullOrEmpty()
    }

    fun isValidUserName(
        userName: String?
    ): String? {

        var errorMSg: String? = null
        if (userName.isNullOrEmpty()) {
            errorMSg = mApplication?.getString(R.string.invalid_user_name)
            return errorMSg;
        }
        return errorMSg;
    }

    fun isValidPassword(
        userName: String?
    ): String? {

        var errorMSg: String? = null
        if (userName.isNullOrEmpty()) {
            errorMSg = mApplication?.getString(R.string.invalid_password)
            return errorMSg;
        }
        return errorMSg;
    }

}