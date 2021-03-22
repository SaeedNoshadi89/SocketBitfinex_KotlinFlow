package com.sn.data.entity

import com.sn.domain.entity.SubscribeOrderBook
import com.sn.domain.entity.SubscribeTicker
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

abstract class BaseSubscribeRequest(
    @Json(name = "event")
    open val event: String,
    @Json(name = "channel")
    open val channel: String,
    @Json(name = "pair")
    open val pair: String
)

@JsonClass(generateAdapter = true)
data class SubscribeTickerRequest(
    override val event: String,
    override val channel: String,
    override val pair: String
) : BaseSubscribeRequest(event, channel, pair)

fun SubscribeTicker.toSubcribeTickerRequest() =
    SubscribeTickerRequest(
        event = event,
        channel = channel,
        pair = pair
    )

@JsonClass(generateAdapter = true)
class SubscribeOrderBookRequest(
    override val event: String,
    override val channel: String,
    override val pair: String,
    @Json(name = "freq")
    val frequency: String
) : BaseSubscribeRequest(event, channel, pair)

fun SubscribeOrderBook.toSubcribeOrderBookrRequest() =
    SubscribeOrderBookRequest(
        event = event,
        channel = channel,
        pair = pair,
        frequency = frequency
    )