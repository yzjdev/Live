package app.live.droid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(val customViewModel: Boolean = false) : Fragment() {

    protected lateinit var rootView: View

    protected lateinit var viewModel: VM

    private var _binding: VB? = null

    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = if (customViewModel) ViewModelProvider(this, createCustomViewModelIfNeed()!!)[createViewModelClass()] else ViewModelProvider(this)[createViewModelClass()]
        _binding = createViewBinding(inflater, container)
        rootView = binding.root
        initView()
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun initView() {}

    open fun createCustomViewModelIfNeed(): ViewModelProvider.Factory? = null

    abstract fun createViewModelClass(): Class<VM>

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

}

