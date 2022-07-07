package com.adyen.android.assignment

import android.app.Application
import com.adyen.android.assignment.data.dataModule
import com.adyen.android.assignment.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AdyenApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@AdyenApplication)
            modules(dataModule, uiModule)
        }
    }
}