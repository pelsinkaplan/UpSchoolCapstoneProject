package com.pelsinkaplan.upschoolcapstoneproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Pelşin KAPLAN on 13.06.2022.
 */

@HiltAndroidApp
class ApplicationDI : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}