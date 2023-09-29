package app.live.droid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.live.droid.utils.ActivityUtils

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtils.removeActivity(this)
    }
}