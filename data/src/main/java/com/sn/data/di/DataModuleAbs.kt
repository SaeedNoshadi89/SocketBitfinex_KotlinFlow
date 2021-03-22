package com.sn.data.di

import com.sn.data.remote.BitfinexDataSource
import com.sn.data.remote.BitfinexDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleAbs {

    @Binds
    abstract fun bindBitfinexDataSource(
        bitfinexDataSourceImpl: BitfinexDataSourceImpl
    ): BitfinexDataSource
}