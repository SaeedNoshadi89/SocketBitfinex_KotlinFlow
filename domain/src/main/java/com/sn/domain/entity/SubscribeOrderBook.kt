package com.sn.domain.entity

class SubscribeOrderBook(
    override val event: String,
    override val channel: String,
    override val pair: String,
    val frequency: String
) : BaseSubscribe(event, channel, pair)