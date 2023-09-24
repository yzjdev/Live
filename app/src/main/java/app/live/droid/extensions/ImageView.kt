package app.live.droid.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url:String){
    Glide.with(context).load(url).into(this)
}