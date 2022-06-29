package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.beforelogin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.data.model.BannerModel
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentBannerBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentProductDetailBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.BannerAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.FavoritesAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnNavigateListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnProductNavigateClickListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin.FavoritesFragmentDirections

class BannerFragment : Fragment() {
    private lateinit var binding: FragmentBannerBinding
    private lateinit var adapter: BannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerList = listOf(
            BannerModel(R.drawable.ic_first_banner_item, "Welcome to bazaar e-commerce app."),
            BannerModel(
                R.drawable.ic_third_banner_item,
                "You can find any Versace product in there."
            ),
            BannerModel(
                R.drawable.ic_second_banner_item,
                "All you have to do is become a member and shop as you wish."
            )
        )
        adapter = BannerAdapter(bannerList, object : OnNavigateListener {
            override fun navigate() {
                Navigation.findNavController(view)
                    .navigate(BannerFragmentDirections.actionBannerFragmentToStartFragment())
            }
        })
        binding.bannerViewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.bannerViewPager2) { tab, position ->
            //Some implementation
        }.attach()

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(BannerFragmentDirections.actionBannerFragmentToStartFragment())
        }

        val sharedPreferencesEdit = requireActivity().getSharedPreferences(
            "MorParaSharedPreferences",
            AppCompatActivity.MODE_PRIVATE
        ).edit()
        sharedPreferencesEdit.putBoolean("firstLogin", true)
        sharedPreferencesEdit.apply()


    }
}