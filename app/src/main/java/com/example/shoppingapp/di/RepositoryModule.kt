package com.example.shoppingapp.di


import com.example.shoppingapp.data.local.ProductDao
import com.example.shoppingapp.data.repository.ProductsRepository
import com.example.shoppingapp.data.retrofit.RemoteDAOInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(remoteDAOInterface: RemoteDAOInterface, productDao: ProductDao): ProductsRepository =
        ProductsRepository(remoteDAOInterface,productDao)


}


