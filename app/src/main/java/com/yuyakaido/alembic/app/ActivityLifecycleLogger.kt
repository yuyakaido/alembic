package com.yuyakaido.alembic.app

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import timber.log.Timber

class ActivityLifecycleLogger : ActivityLifecycleCallbacks {

    private fun print(lifecycle: String, activity: String) {
        Timber.tag("ActivityLifecycle").d("$lifecycle: $activity")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        print("onActivityCreated", activity.javaClass.simpleName)
    }

    override fun onActivityStarted(activity: Activity) {
        print("onActivityStarted", activity.javaClass.simpleName)
    }

    override fun onActivityResumed(activity: Activity) {
        print("onActivityResumed", activity.javaClass.simpleName)
    }

    override fun onActivityPaused(activity: Activity) {
        print("onActivityPaused", activity.javaClass.simpleName)
    }

    override fun onActivityStopped(activity: Activity) {
        print("onActivityStopped", activity.javaClass.simpleName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        print("onActivitySaveInstanceState", activity.javaClass.simpleName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        print("onActivityDestroyed", activity.javaClass.simpleName)
    }
}
