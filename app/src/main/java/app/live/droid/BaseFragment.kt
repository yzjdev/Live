package app.live.droid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    var flag = false
    protected val model by lazy {
        if (flag) {
            getViewModel()!!
        } else
            createViewModelLazy(getViewModelClass(), { viewModelStore }).value
    }


    private var _binding: VB? = null

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val method = getViewBindingClass().getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        @Suppress("UNCHECKED_CAST")
        _binding = method.invoke(null, inflater, container, false) as VB
        initData()
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    open fun initData() {}

    abstract fun getViewModelClass(): KClass<VM>

    abstract fun getViewBindingClass(): Class<VB>

    open fun getViewModel(): VM? {
        return null
    }
}

