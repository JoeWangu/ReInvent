package com.saddict.reinvent

import android.app.Application
import com.saddict.reinvent.data.manager.ReInventContainer
import com.saddict.reinvent.data.manager.ReInventRepository

class ReInventApplication: Application() {
    lateinit var container: ReInventContainer
    override fun onCreate() {
        super.onCreate()
        container = ReInventRepository(this)
    }
}