package com.sn.domain.repository

import com.sn.domain.entity.SubscribeOrderBook
import com.sn.domain.entity.SubscribeTicker
import com.sn.domain.model.OrderBookData
import com.sn.domain.model.TickerData
import kotlinx.coroutines.flow.Flow


interface BitfinexRepository {

    fun observeTicker(subscribeTicker: SubscribeTicker): Flow<TickerData>

    fun observeOrderBook(subscribeOrderBook: SubscribeOrderBook): Flow<OrderBookData>
}