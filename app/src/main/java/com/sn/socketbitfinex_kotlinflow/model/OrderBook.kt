package com.sn.socketbitfinex_kotlinflow.model

import com.sn.domain.model.OrderBookData

data class OrderBook(
    val amount: Double,
    val count: Int,
    val price: Double
)

fun OrderBookData.toOrderBookModel() = OrderBook(
    amount = amount,
    count = count,
    price = price
)