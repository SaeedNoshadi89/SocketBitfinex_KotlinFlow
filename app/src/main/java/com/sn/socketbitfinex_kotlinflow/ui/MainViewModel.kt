package com.sn.socketbitfinex_kotlinflow.ui

import androidx.lifecycle.ViewModel
import com.sn.domain.util.UseCase
import com.sn.socketbitfinex_kotlinflow.UseCases
import com.sn.socketbitfinex_kotlinflow.model.OrderBook
import com.sn.socketbitfinex_kotlinflow.model.Ticker
import com.sn.socketbitfinex_kotlinflow.model.toOrderBookModel
import com.sn.socketbitfinex_kotlinflow.model.toTickerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    fun getOrderBook(): Flow<List<OrderBook>> = flow {
        useCases.orderBookUseCase.execute(UseCase.None())
            .map { orderBookData ->
                orderBookData.toOrderBookModel()
            }.map { orderBook ->
                orderBook.buildOrderBooks()
            }.collect {
                emit(it)
            }
    }.flowOn(Dispatchers.IO)

    fun getTicker(): Flow<Ticker> = flow {
        useCases.tickerUseCase.execute(UseCase.None())
            .map { tickerData ->
                tickerData.toTickerModel()
            }.collect {
                emit(it)
            }
    }.flowOn(Dispatchers.IO)
}