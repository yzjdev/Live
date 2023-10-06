package app.live.droid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import app.live.droid.utils.ActivityUtils

abstract class BaseActivity<VB :ViewBinding,VM:ViewModel>(val customViewModel: Boolean = false) : AppCompatActivity() {

    protected lateinit var viewModel:VM
    protected lateinit var binding:VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivity(this)
        viewModel = if (customViewModel) ViewModelProvider(this, createCustomViewModelIfNeed()!!)[createViewModelClass()] else ViewModelProvider(this)[createViewModelClass()]
        binding = createViewBinding()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtils.removeActivity(this)
    }

    open fun createCustomViewModelIfNeed(): ViewModelProvider.Factory? = null

    abstract fun createViewModelClass(): Class<VM>

    abstract fun createViewBinding():VB


}