package app.live.droid

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import app.live.droid.base.BaseActivity
import app.live.droid.databinding.ActivityMainBinding
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class MainActivity : BaseActivity() {
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


    override fun onBackPressed() {
        findViewById<SearchView>(R.id.searchView).apply {
            if (isShowing){
                hide()
                return
            }
        }
        super.onBackPressed()
    }

    fun getSearchView() = findViewById<SearchView>(R.id.searchView)

    fun getSearchBar() = findViewById<SearchBar>(R.id.openSearchBar)

}
