package com.riyas.topstories.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.riyas.topstories.R
import com.riyas.topstories.model.AppConstants
import com.riyas.topstories.view.fragments.DetailsFragment
import com.riyas.topstories.view.fragments.HomeFragment
import com.riyas.topstories.view.fragments.LoginFragment
import com.wisilica.imsafe.model.AppThemeMode

class MainActivity : AppCompatActivity() {
    lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = AppThemeMode(this)
        when (theme.themeMode) {
            AppConstants.AppTheme.DARK_THEME -> {
                delegate.setLocalNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            }
            else -> {
                delegate.setLocalNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        setContentView(R.layout.activity_main)
        mNavController = findNavController(R.id.my_nav_host_fragment)
    }

    override fun onBackPressed() {
        val mNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        val currentFragment = mNavHostFragment?.childFragmentManager?.fragments?.get(0)
        if (currentFragment is HomeFragment || currentFragment is LoginFragment) {
            finish()
            return
        }
        if (currentFragment is DetailsFragment) {
            mNavController.navigate(R.id.goBackToHomeFragment)
        }
    }
}
