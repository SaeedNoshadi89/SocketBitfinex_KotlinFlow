package com.sn.socketbitfinex_kotlinflow.model

data class Ticker(
    var dailyChange: String,
    var lastPrice: String,
    var volume: String,
    var high: String,
    var low: String
)

fun com.sn.domain.model.TickerData.toTickerModel() = Ticker(
    dailyChange = dailyChange,
    lastPrice = lastPrice,
    volume = volume,
    high = high,
    low = low
)