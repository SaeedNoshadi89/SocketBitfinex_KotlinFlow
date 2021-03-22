package com.sn.data.remote

import android.annotation.SuppressLint
import com.sn.data.entity.SubscribeOrderBookRequest
import com.sn.data.entity.SubscribeTickerRequest
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@SuppressLint("CheckResult")
class BitfinexDataSourceImpl @Inject constructor(private val bitfinexApi: BitfinexApi) :
    BitfinexDataSource {

    private val TICKER_SNAPSHOT_SIZE = 11
    private val ORDERBOOK_SNAPSHOT_SIZE = 4

    override fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flow<Array<String>> =
        flow {
            openWebSocketEvent()
                .filter {
                    it is WebSocket.Event.OnConnectionOpened<*>
                }.collect {
                    bitfinexApi.apply {
                        sendTickerRequest(subscribeTickerRequest)
                        observeTicker()
                            .filter {
                                it.size == TICKER_SNAPSHOT_SIZE
                            }.collect {
                                emit(it)
                            }
                    }
                }
        }.flowOn(Dispatchers.IO)

    override fun subscribeOrderBook(subscribeOrderBookRequest: SubscribeOrderBookRequest): Flow<DoubleArray> =
        flow {
            openWebSocketEvent()
                .filter {
                    it is WebSocket.Event.OnConnectionOpened<*>
                }.collect {
                    bitfinexApi.apply {
                        sendOrderBookRequest(subscribeOrderBookRequest)
                        observeOrderBook()
                            .filter {
                                it.size == ORDERBOOK_SNAPSHOT_SIZE
                            }.collect {
                                emit(it)
                            }
                    }
                }
        }.flowOn(Dispatchers.IO)

    override fun openWebSocketEvent(): Flow<WebSocket.Event> = flow {
        bitfinexApi.openWebSocketEvent().collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)
}
