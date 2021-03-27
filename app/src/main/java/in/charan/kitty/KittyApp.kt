package `in`.charan.kitty

import android.app.Application
import android.content.Context

/**
 * Created by Charan on March 27, 2021
 */
class KittyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}