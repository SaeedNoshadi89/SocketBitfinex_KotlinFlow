package com.sn.domain.model

data class OrderBookData(
    val amount: Double,
    val count: Int,
    val price: Double
)

fun DoubleArray.toOrderBookData() = OrderBookData(
    amount = this[1],
    count = this[2].toInt(),
    price = this[3]
)
