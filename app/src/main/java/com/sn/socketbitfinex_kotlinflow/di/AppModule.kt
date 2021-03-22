package com.sn.socketbitfinex_kotlinflow.di

import com.sn.data.repository.BitfinexRepositoryImpl
import com.sn.domain.usecase.OrderBookUseCase
import com.sn.domain.usecase.TickerUseCase
import com.sn.socketbitfinex_kotlinflow.ui.MainViewModel
import com.sn.socketbitfinex_kotlinflow.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideMainViewModel(useCases: UseCases) =
        MainViewModel(useCases)

    @Provides
    fun provideUseCases(bitfinexRepositoryImpl: BitfinexRepositoryImpl) = UseCases(
        OrderBookUseCase(bitfinexRepositoryImpl),
        TickerUseCase(bitfinexRepositoryImpl)
    )

 /*   @Singleton
    @Provides
    fun provideScarletRepository(bitfinexApi: BitfinexApi) =
        ScarletRepository(bitfinexApi)*/
}