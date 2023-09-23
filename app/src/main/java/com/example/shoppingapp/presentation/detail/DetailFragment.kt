package com.example.shoppingapp.presentation.detail

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shoppingapp.R
import com.example.shoppingapp.common.loadImage
import com.example.shoppingapp.common.viewBinding
import com.example.shoppingapp.data.model.AddToCartRequest
import com.example.shoppingapp.databinding.FragmentDetailBinding
import com.example.shoppingapp.presentation.home.HomeState
import com.example.shoppingapp.presentation.home.HomeViewModel
import com.example.shoppingapp.presentation.signin.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

  private val binding by viewBinding(FragmentDetailBinding::bind)

  private val viewModel by viewModels<DetailViewModel>()

  private val args by navArgs<DetailFragmentArgs>()




  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.getProductDetail(args.id)

    observeData()

    with(binding)
    {
      btnBack.setOnClickListener {
        findNavController().navigateUp()
      }

      addtocard.setOnClickListener {
        val addToCartRequest = AddToCartRequest(viewModel.currentUser!!.uid,args.id)
        viewModel.addToCart(addToCartRequest)
        viewModel.detailState.observe(viewLifecycleOwner){
          state ->
          if (state is DetailState.PostResponse)
          {
            Toast.makeText(
              requireContext(),
              state.base.message,
              Toast.LENGTH_SHORT
            ).show()
          }
        }
      }
    }


  }

  private fun observeData() {

    with(binding)
    {

      viewModel.detailState.observe(viewLifecycleOwner) { state ->
        when (state)
        {

          is DetailState.Data -> {

            with(state) {
              productImage.loadImage(product.imageOne)
              producttitle.text = product.title
              productDesc.text = product.description
              ratingBar.rating = product.rate!!.toFloat()

              if(product.saleState == true){

                productPrice.setTextColor(Color.RED)
                salePrice.text=product.salePrice.toString()
              }else{
                productPrice.text = "₺${product.price}"
                salePrice.visibility= View.GONE
              }
            }
          }

          is DetailState.Error -> {
            Toast.makeText(
              requireContext(),
              state.throwable.message.orEmpty(),
              Toast.LENGTH_SHORT
            ).show()
          }

          DetailState.Loading ->
          {

          }

          is DetailState.PostResponse -> {
            Toast.makeText(
              requireContext(),
              state.base.message,
              Toast.LENGTH_SHORT
            ).show()
          }
        }

      }

    }



  }
}