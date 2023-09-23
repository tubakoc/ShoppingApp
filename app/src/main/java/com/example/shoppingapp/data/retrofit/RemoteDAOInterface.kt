package com.example.shoppingapp.data.retrofit

import com.example.shoppingapp.data.model.AddToCartRequest
import com.example.shoppingapp.data.model.BaseResponse
import com.example.shoppingapp.data.model.ClearCartRequest
import com.example.shoppingapp.data.model.DeleteFromCartRequest
import com.example.shoppingapp.data.model.GetCartProductsResponse
import com.example.shoppingapp.data.model.GetProductDetail
import com.example.shoppingapp.data.model.GetProductsResponce
import com.example.shoppingapp.data.model.GetSaleProductResponse
import com.example.shoppingapp.data.model.GetSearchProductResponse
import com.example.shoppingapp.utils.Constants.Endpoint.ADD_TO_CARD
import com.example.shoppingapp.utils.Constants.Endpoint.CLEAR_CART
import com.example.shoppingapp.utils.Constants.Endpoint.DELETE_FROM_CARD
import com.example.shoppingapp.utils.Constants.Endpoint.GET_CARD_PRODUCTS
import com.example.shoppingapp.utils.Constants.Endpoint.GET_PRODUCTS
import com.example.shoppingapp.utils.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.shoppingapp.utils.Constants.Endpoint.GET_SALE_PRODUCTS
import com.example.shoppingapp.utils.Constants.Endpoint.SEARCH_PRODUCT
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteDAOInterface {

    @Headers("store:shoppingdata")
    @GET(GET_PRODUCTS)
    suspend fun getAllProducts(): GetProductsResponce

    @Headers("store:shoppingdata")
    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): GetProductDetail

    @Headers("store:shoppingdata")
    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): GetSaleProductResponse

    @Headers("store:shoppingdata")
    @GET(GET_CARD_PRODUCTS)
    suspend fun getCartProducts(
        @Query("userId") userId: String
    ): GetCartProductsResponse

    @Headers("store:shoppingdata")
    @GET(SEARCH_PRODUCT)
    suspend fun getSearchProduct(
        @Query("query") query: String
    ): GetSearchProductResponse

    @Headers("store:shoppingdata")
    @POST(ADD_TO_CARD)
    suspend fun addToCart(
        @Body addToCartRequest : AddToCartRequest
    ) : BaseResponse

    @Headers("store:shoppingdata")
    @POST(DELETE_FROM_CARD)
    suspend fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): BaseResponse

    @Headers("store:shoppingdata")
    @POST(CLEAR_CART)
    suspend fun clearCart(
        @Body clearCartRequest: ClearCartRequest
    ) : BaseResponse
}