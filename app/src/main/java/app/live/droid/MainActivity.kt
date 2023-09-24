package app.live.droid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import app.live.droid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView
        navView.itemIconTintList = null
        val navHost = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHost.navController
        navView.setupWithNavController(navController)
    }

}
