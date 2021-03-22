package com.sn.domain.util

import kotlinx.coroutines.flow.Flow

interface UseCase {

    interface RequestUseCase<P : Params, T : Any> {
        fun execute(params: P): T
    }

    interface FlowUseCase<P : Params, T : Any> : UseCase {
        fun execute(params: P): Flow<T>
    }

    abstract class Params

    class None : Params()
}