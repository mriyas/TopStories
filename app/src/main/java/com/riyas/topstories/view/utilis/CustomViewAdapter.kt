package com.riyas.topstories.view.utilis

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import android.R
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import android.webkit.WebView
import android.webkit.WebViewClient




@BindingAdapter("animate")
fun animate(imageView: ImageView,duration: Long) {
    val rotate = RotateAnimation(
        0f,
        180f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    rotate.duration = duration
    rotate.interpolator = LinearInterpolator()
rotate.repeatMode=RotateAnimation.INFINITE
    rotate.fillAfter=true
    rotate.repeatCount=RotateAnimation.INFINITE
    val image = imageView

    //image.startAnimation(rotate)
}

@BindingAdapter("is_selected")
fun setSelected(view: View, status : Int) {
    var isSelected = view.tag
    view.isSelected = (isSelected == status.toString())

}


@BindingAdapter("setWebViewClient")
fun setWebViewClient(view: WebView, client: WebViewClient) {
    view.webViewClient = client
}

@BindingAdapter("loadUrl")
fun loadUrl(view: WebView, url: String) {
    view.loadUrl(url)
    view.settings.javaScriptEnabled=true
}

