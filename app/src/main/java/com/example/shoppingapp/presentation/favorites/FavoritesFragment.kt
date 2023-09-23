package com.example.shoppingapp.presentation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.shoppingapp.R
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.databinding.FragmentFavoritesBinding
import com.example.shoppingapp.presentation.adapters.FavoriteAdapter
import com.example.shoppingapp.presentation.adapters.FavoriteListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), FavoriteListener {


    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<FavoritesViewModel>()
    private val favoriteProductsAdapter by lazy { FavoriteAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteProducts()

        with(binding)
        {

            rcFav.adapter = favoriteProductsAdapter
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->

            when (state) {

                FavoriteState.Loading -> {

                }

                is FavoriteState.Data -> {
                    binding.rcFav.visibility = View.VISIBLE
                    favoriteProductsAdapter.submitList(state.products)

                }

                is FavoriteState.Error -> {
                    binding.rcFav.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is FavoriteState.PostResponse -> {
                    Toast.makeText(
                        requireContext(),
                        state.base.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    override fun onFavoriteDelete(product : ProductUI) {

        viewModel.removeFromFavorites(product)
   }

    override fun onAddCard(id: Int) {

        //viewModel.addToCart(id)
    }
}