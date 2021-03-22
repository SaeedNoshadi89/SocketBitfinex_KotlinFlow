package com.sn.domain.entity

class SubscribeTicker(
    override val event: String,
    override val channel: String,
    override val pair: String
) : BaseSubscribe(event, channel, pair)