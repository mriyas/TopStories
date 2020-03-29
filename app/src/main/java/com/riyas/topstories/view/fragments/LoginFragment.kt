package com.riyas.topstories.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.riyas.topstories.R
import com.riyas.topstories.databinding.FragmentLoginBinding
import com.riyas.topstories.model.AppConstants
import com.riyas.topstories.model.SignInErrors
import com.riyas.topstories.model.UserDetails
import com.riyas.topstories.view.activity.MainActivity
import com.riyas.topstories.view_model.UserViewModel
import com.wisilica.imsafe.model.AppThemeMode
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment:BaseFragment() {
    val mUserViewModel: UserViewModel by viewModels()
    lateinit var   theme:AppThemeMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserViewModel?.mState.observe(this, Observer {
            when(it.state){
                AppConstants.ACTION_LOGIN_CLICK->{
                    findNavController().navigate(R.id.goToHomeFragment)
                }
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ) as FragmentLoginBinding


        binding.errorModel = SignInErrors(null)
        binding.viewModel = mUserViewModel
        binding.fragment=this
        binding.data = UserDetails(null,null)
        binding.lifecycleOwner=viewLifecycleOwner
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        theme = AppThemeMode(activity!!)
        when (theme.themeMode) {
            AppConstants.AppTheme.DARK_THEME -> {
                sw_theme.isChecked=true
            }
            else -> {
               sw_theme.isChecked=false
            }
        }
    }

    fun changeTheme(){
        when (sw_theme.isChecked) {
            true -> {
               theme.themeMode=AppConstants.AppTheme.DARK_THEME
            }
            else -> {
                theme.themeMode=AppConstants.AppTheme.DAY_THEME
            }
        }
        theme.save()
        activity!!.startActivity(Intent(activity!!,MainActivity::class.java))
        activity!!.finish()
    }
}