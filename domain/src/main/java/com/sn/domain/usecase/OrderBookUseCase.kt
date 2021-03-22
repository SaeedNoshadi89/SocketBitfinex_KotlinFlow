package com.sn.domain.usecase

import com.sn.domain.entity.BaseSubscribe
import com.sn.domain.entity.SubscribeOrderBook
import com.sn.domain.model.OrderBookData
import com.sn.domain.repository.BitfinexRepository
import com.sn.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrderBookUseCase(private val bitfinexRepository: BitfinexRepository) :
    UseCase.FlowUseCase<UseCase.None, OrderBookData> {
    override fun execute(params: UseCase.None): Flow<OrderBookData> = flow {
        SubscribeOrderBook(
            event = BaseSubscribe.SUBSCRIBE_EVENT,
            channel = BaseSubscribe.ORDER_BOOK_CHANNEL,
            pair = BaseSubscribe.BTCUSD_PAIR,
            frequency = BaseSubscribe.FREQUENCY_ZERO
        ).run {
            bitfinexRepository.observeOrderBook(this).collect {
                emit(it)
            }
        }
    }.flowOn(Dispatchers.IO)
}