package com.sn.data.repository

import com.sn.data.entity.toSubcribeOrderBookrRequest
import com.sn.data.entity.toSubcribeTickerRequest
import com.sn.data.remote.BitfinexDataSource
import com.sn.domain.entity.SubscribeOrderBook
import com.sn.domain.entity.SubscribeTicker
import com.sn.domain.model.OrderBookData
import com.sn.domain.model.TickerData
import com.sn.domain.model.toOrderBookData
import com.sn.domain.model.toTickerData
import com.sn.domain.repository.BitfinexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BitfinexRepositoryImpl @Inject constructor(private val bitfinexDataSource: BitfinexDataSource) :
    BitfinexRepository {

    override fun observeTicker(subscribeTicker: SubscribeTicker): Flow<TickerData> = flow {
        bitfinexDataSource.subscribeTicker(subscribeTicker.toSubcribeTickerRequest())
            .map { response -> response.toTickerData() }
            .collect {
                emit(it)
            }
    }.flowOn(Dispatchers.IO)

    override fun observeOrderBook(subscribeOrderBook: SubscribeOrderBook): Flow<OrderBookData> = flow {
            bitfinexDataSource.subscribeOrderBook(subscribeOrderBook.toSubcribeOrderBookrRequest())
                .map { response -> response.toOrderBookData() }
                .collect {
                    emit(it)
                }
        }.flowOn(Dispatchers.IO)
}
