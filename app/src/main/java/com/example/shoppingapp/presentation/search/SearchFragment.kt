package com.example.shoppingapp.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.common.invisible
import com.example.shoppingapp.common.viewBinding
import com.example.shoppingapp.common.visible
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.databinding.FragmentSearchBinding
import com.example.shoppingapp.presentation.adapters.SearchAdapter
import com.example.shoppingapp.presentation.adapters.SearchProductListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),SearchProductListener{

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()
    private val searchProductsAdapter by lazy { SearchAdapter(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            rvSearchProducts.adapter = searchProductsAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.getProductSearch(p0.toString())
                    return false
                }
            })
        }
        observeData()
    }

    private fun observeData() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            when (state) {

                SearchState.Loading -> {
                    binding.progressBar.visible()
                }

                is SearchState.Data -> {
                    searchProductsAdapter.submitList(state.products)
                    binding.progressBar.invisible()
                }

                is SearchState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.invisible()
                }

            }

        }

    }

    override fun onProductClick(id: Int) {
        val action = SearchFragmentDirections.searchToDetail(id)
        findNavController().navigate(action)
    }

    override fun onFavButtonClick(product: ProductUI) {
viewModel.addToFavorites(product)
    }


}