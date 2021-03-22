package com.sn.data.remote

import com.sn.data.entity.JsonResponse
import com.sn.data.entity.SubscribeOrderBookRequest
import com.sn.data.entity.SubscribeTickerRequest
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface BitfinexApi {

    @Receive
    fun openWebSocketEvent(): Flow<WebSocket.Event>

    @Receive
    fun receiveResponse(): Flow<JsonResponse>

    @Send
    fun sendTickerRequest(subscribeTickerRequest: SubscribeTickerRequest)

    @Receive
    fun observeTicker(): Flow<Array<String>>

    @Send
    fun sendOrderBookRequest(subscribeOrderBookRequest: SubscribeOrderBookRequest)

    @Receive
    fun observeOrderBook(): Flow<DoubleArray>

}