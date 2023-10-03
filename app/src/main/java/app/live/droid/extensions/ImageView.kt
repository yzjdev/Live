package app.live.droid.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadUrl(url:String){
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}