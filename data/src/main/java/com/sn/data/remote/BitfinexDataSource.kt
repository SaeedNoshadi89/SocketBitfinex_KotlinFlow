package com.sn.data.remote

import com.sn.data.entity.SubscribeOrderBookRequest
import com.sn.data.entity.SubscribeTickerRequest
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

interface BitfinexDataSource {

    fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flow<Array<String>>

    fun subscribeOrderBook(subscribeOrderBookRequest: SubscribeOrderBookRequest): Flow<DoubleArray>

    fun openWebSocketEvent(): Flow<WebSocket.Event>
}
