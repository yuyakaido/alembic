package com.yuyakaido.alembic.app

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import com.yuyakaido.alembic.BuildConfig
import timber.log.Timber

class AlembicApp : Application() {

    private val activityLifecycleLogger = ActivityLifecycleLogger()

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        initializeCoil()
        registerActivityLifecycleCallbacks()
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeCoil() {
        SingletonImageLoader.setSafe { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .components {
                    add(OkHttpNetworkFetcherFactory())
                    add { chain ->
                        Timber.tag("ImageLoader").d(chain.request.data.toString())
                        chain.proceed()
                    }
                }
                .build()
        }
    }

    private fun registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(activityLifecycleLogger)
    }

    private fun unregisterActivityLifecycleCallbacks() {
        unregisterActivityLifecycleCallbacks(activityLifecycleLogger)
    }
}
