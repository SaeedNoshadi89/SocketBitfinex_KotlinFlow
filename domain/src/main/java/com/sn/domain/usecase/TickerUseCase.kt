package com.sn.domain.usecase

import com.sn.domain.entity.BaseSubscribe
import com.sn.domain.entity.SubscribeTicker
import com.sn.domain.model.TickerData
import com.sn.domain.repository.BitfinexRepository
import com.sn.domain.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TickerUseCase(private val bitfinexRepository: BitfinexRepository) :
    UseCase.FlowUseCase<UseCase.None, TickerData> {
    override fun execute(params: UseCase.None): Flow<TickerData> = flow {
        SubscribeTicker(
            event = BaseSubscribe.SUBSCRIBE_EVENT,
            channel = BaseSubscribe.TICKER_CHANNEL,
            pair = BaseSubscribe.BTCUSD_PAIR
        ).run {
            bitfinexRepository.observeTicker(this).collect {
                emit(it)
            }
        }
    }.flowOn(Dispatchers.IO)

}