package com.yuyakaido.alembic.app

import android.app.Application
import com.yuyakaido.alembic.BuildConfig
import timber.log.Timber

class AlembicApp : Application() {

    private val activityLifecycleLogger = ActivityLifecycleLogger()

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
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

    private fun registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(activityLifecycleLogger)
    }

    private fun unregisterActivityLifecycleCallbacks() {
        unregisterActivityLifecycleCallbacks(activityLifecycleLogger)
    }
}
