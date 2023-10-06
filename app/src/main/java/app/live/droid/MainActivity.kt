package app.live.droid

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import app.live.droid.base.BaseActivity
import app.live.droid.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun createViewModelClass() = MainViewModel::class.java

    override fun createViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView = binding.navView
        navView.itemIconTintList = null
        val navHost = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHost.navController
        navView.setupWithNavController(navController)
    }


}
