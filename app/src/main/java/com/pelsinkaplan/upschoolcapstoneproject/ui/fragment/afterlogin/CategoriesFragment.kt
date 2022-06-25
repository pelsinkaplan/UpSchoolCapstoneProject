package com.pelsinkaplan.upschoolcapstoneproject.ui.fragment.afterlogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.pelsinkaplan.upschoolcapstoneproject.databinding.FragmentCategoriesBinding
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.CategoriesAdapter
import com.pelsinkaplan.upschoolcapstoneproject.ui.adapter.listener.OnCategoryNavigateListener
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var adapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CategoriesViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewAllProductsButton.setOnClickListener {
            val action =
                CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(null)
            Navigation.findNavController(it).navigate(action)
        }
        var list = arrayListOf<String>()
        CoroutineScope(Dispatchers.Main).launch {
            val response = viewModel.service()
            if (response != null) {
                list = viewModel.service()!!
                list.remove("Shoes")
            }

            adapter = CategoriesAdapter(list, object : OnCategoryNavigateListener {
                override fun navigate(category: String) {
                    val action =
                        CategoriesFragmentDirections.actionCategoriesFragmentToProductsFragment(
                            category
                        )
                    Navigation.findNavController(view).navigate(action)
                }

            })
            binding.productRecyclerView.adapter = adapter
        }
        binding.searchInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                adapter.updateList(searchCategories(p0.toString(), list))
            }

        })
    }

    fun searchCategories(
        filterItem: String,
        list: List<String>
    ): MutableList<String> {
        val tempList: MutableList<String> = ArrayList()
        for (i in list) {
            if (filterItem.lowercase() in i.lowercase()) {
                tempList.add(i)
            }
        }
        return tempList
    }
}