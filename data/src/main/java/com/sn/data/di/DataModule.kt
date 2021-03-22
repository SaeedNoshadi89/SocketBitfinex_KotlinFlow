package com.sn.data.di

import android.app.Application
import com.sn.data.remote.BitfinexApi
import com.sn.data.remote.BitfinexDataSource
import com.sn.data.remote.BitfinexDataSourceImpl
import com.sn.data.repository.BitfinexRepositoryImpl
import com.sn.data.util.Constant
import com.sn.data.util.FlowStreamAdapter
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAppForegroundAndUserLoggedInLifecycle(appContext: Application) =
        AndroidLifecycle.ofApplicationForeground(application = appContext)

    @Singleton
    @Provides
    fun provideBackoffStrategy() =
        ExponentialWithJitterBackoffStrategy(
            Constant.DEFAULT_BASE_DURATION_IN_MS,
            Constant.DEFAULT_MAX_DURATION_IN_MS
        )

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(Constant.DEFAULT_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .readTimeout(Constant.DEFAULT_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .writeTimeout(Constant.DEFAULT_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideScarletInstance(
        okHttpClient: OkHttpClient,
        backoffStrategy: ExponentialWithJitterBackoffStrategy,
        lifecycle: Lifecycle
    ): Scarlet = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(Constant.BASE_URL))
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
        .addStreamAdapterFactory(FlowStreamAdapter.Factory)
        .backoffStrategy(backoffStrategy)
        .lifecycle(lifecycle)
        .build()

    @Singleton
    @Provides
    fun provideScarletService(scarlet: Scarlet) = scarlet.create<BitfinexApi>()

    @Singleton
    @Provides
    fun provideBitfinexRepositoryImpl(bitfinexDataSource: BitfinexDataSource) = BitfinexRepositoryImpl(bitfinexDataSource)

    @Singleton
    @Provides
    fun provideBitfinexDataSourceImpl(bitfinexApi: BitfinexApi) = BitfinexDataSourceImpl(bitfinexApi)
}