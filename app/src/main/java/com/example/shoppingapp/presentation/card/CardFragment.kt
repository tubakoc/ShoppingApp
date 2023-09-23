package com.example.shoppingapp.presentation.card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shoppingapp.R
import com.example.shoppingapp.data.model.ClearCartRequest
import com.example.shoppingapp.databinding.FragmentCardBinding
import com.example.shoppingapp.presentation.adapters.CardAdapter
import com.example.shoppingapp.presentation.adapters.CardListener
import com.example.shoppingapp.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFragment : Fragment(R.layout.fragment_card),CardListener {


    private lateinit var binding: FragmentCardBinding
    private val cartAdapter by lazy { CardAdapter(this) }
    private val viewModel by viewModels<CardViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        with(binding)
        {

            recyclerView.adapter = cartAdapter

            deleteCard.setOnClickListener {
                val clearCartRequest = viewModel.currentUser!!.uid?.let { it1 ->
                    ClearCartRequest(
                        it1
                    )
                }
                if (clearCartRequest != null) {
                    viewModel.clearCart(clearCartRequest)
                }
            }

            btnPaymen.setOnClickListener {
                navigatePaymentScrenn()
            }
        }

        with(viewModel)
        {

            viewModel.currentUser!!.uid?.let { getCartProducts(it) }
        }
    }


    private fun observeData() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->

            when (state) {

                CartState.Loading -> {
                }

                is CartState.CartList -> {
                    cartAdapter.submitList(state.products)
                }

                is CartState.PostResponse -> {
                    Toast.makeText(
                        requireContext(),
                        state.base.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is CartState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

    override fun onCardClick(id: Int) {
        val action = CardFragmentDirections.cardToDetail(id)
        findNavController().navigate(action)

    }

    override fun onDeleteClick(id: Int) {

        viewModel.deleteFromCart(id)
    }

 private fun navigatePaymentScrenn()
 {
     val action = CardFragmentDirections.cardToPayment()
     findNavController().navigate(action)
 }

}