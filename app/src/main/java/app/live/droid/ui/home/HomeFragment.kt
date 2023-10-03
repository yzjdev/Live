package app.live.droid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import app.live.droid.base.BaseFragment
import app.live.droid.databinding.FragmentHomeBinding
import app.live.droid.ui.recommend.RecommendFragment
import app.live.droid.utils.LiveHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getViewModelClass() = HomeViewModel::class

    override fun getViewBindingClass() = FragmentHomeBinding::class.java

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


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val f = fragments[tab?.position!!] as RecommendFragment
                f.re()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


    }


}