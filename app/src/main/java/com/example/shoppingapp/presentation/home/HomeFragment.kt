package com.example.shoppingapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.common.viewBinding
import com.example.shoppingapp.data.model.ProductUI
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.presentation.adapters.ProductAdapter
import com.example.shoppingapp.presentation.adapters.ProductListener
import com.example.shoppingapp.presentation.adapters.ProductSaleAdapter
import com.example.shoppingapp.presentation.adapters.ProductSaleListener
import com.example.shoppingapp.presentation.signin.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ProductListener, ProductSaleListener {


    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val productsAdapter by lazy { ProductAdapter(this) }
    private val productSaleAdapter by lazy { ProductSaleAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAllProducts.adapter = productsAdapter

        binding.rvSaleProducts.adapter = productSaleAdapter

        viewModel.getAllProducts()
        viewModel.getSaleProducts()

        observeData()
    }

    private fun observeData() {

        viewModel.homeState.observe(viewLifecycleOwner)
        { state ->
            when (state) {
                HomeState.Loading -> {
                }

                is HomeState.ProductList -> {
                    productsAdapter.submitList(state.products)
                }

                is HomeState.SaleProductList -> {
                    productSaleAdapter.submitList(state.saleProducts)
                }

                is HomeState.PostResponse -> {
                    Toast.makeText(
                        requireContext(),
                        state.base.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is HomeState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    override fun onProductClick(id: Int) {
        navigateDetailScreen(id)
    }

    override fun onAddFavorite(product: ProductUI) {
        viewModel.addToFavorite(product)
    }

    override fun onSaleProductClick(id: Int) {
      //  navigateDetailScreen(id)
    }

    override fun onSaleCartClick(id: Int) {
        navigateDetailScreen(id)
    }

    private fun navigateDetailScreen(id: Int) {
        val action = HomeFragmentDirections.homeToDetail(id)
        findNavController().navigate(action)
    }

}