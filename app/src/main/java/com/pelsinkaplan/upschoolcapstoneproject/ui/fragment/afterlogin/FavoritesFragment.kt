package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.pelsinkaplan.upschoolcapstoneproject.data.model.Product
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentFavoritesBinding
import com.pelsinkaplan.upschoolcapstoneproject.service.room.FavoritesDatabase
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.FavoritesAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnProductNavigateClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: FavoritesAdapter
    private lateinit var favoritesDatabase: FavoritesDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesDatabase = FavoritesDatabase.getFavoritesDatabase(requireContext())!!
        val favoritesList = favoritesDatabase.getFavoritesDao().getAll()
        if (favoritesList.isEmpty())
            binding.emptyTextView.visibility = View.VISIBLE
        adapter = FavoritesAdapter(favoritesList, object : OnProductNavigateClickListener {
            override fun navigate(product: Product) {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailFragment(
                        product
                    )
                Navigation.findNavController(view).navigate(action)
            }

        })
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.favoritesRecyclerView.layoutManager = layoutManager
        layoutManager.orientation = GridLayoutManager.VERTICAL
        binding.favoritesRecyclerView.adapter = adapter
    }
}
