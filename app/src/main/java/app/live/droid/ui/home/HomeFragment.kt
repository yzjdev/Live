package app.live.droid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import app.live.droid.base.BaseFragment
import app.live.droid.databinding.FragmentHomeBinding
import app.live.droid.ui.recommend.RecommendFragment
import app.live.droid.utils.LiveHelper
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun createViewModelClass() = HomeViewModel::class.java

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = LiveHelper.getPlatforms()
        val fragments = ArrayList<RecommendFragment>()
        list.forEach { parser -> fragments.add(RecommendFragment(parser)) }

        val adapter = HomePagerAdapter(context as FragmentActivity, fragments)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = list[position].name
        }.attach()

    }


}