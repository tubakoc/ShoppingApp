package com.example.shoppingapp.utils

import com.example.shoppingapp.data.retrofit.RemoteDAOInterface
import com.example.shoppingapp.data.retrofit.RetrofitClient
import com.example.shoppingapp.utils.Constants.BASE_URL

class ApiUtils {

    companion object {

        fun getProductsDAOInterface(): RemoteDAOInterface {
            return RetrofitClient.getClient(BASE_URL).create(RemoteDAOInterface::class.java)
        }

    }


}