package com.sn.socketbitfinex_kotlinflow

import com.sn.domain.usecase.OrderBookUseCase
import com.sn.domain.usecase.TickerUseCase

data class UseCases(
    val orderBookUseCase: OrderBookUseCase,
    val tickerUseCase: TickerUseCase
)
