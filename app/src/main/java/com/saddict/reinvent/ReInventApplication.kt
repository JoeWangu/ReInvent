package com.saddict.reinvent

import android.app.Application
import com.saddict.reinvent.data.ReInventContainer
import com.saddict.reinvent.data.ReInventRepository

class ReInventApplication: Application() {
    lateinit var container: ReInventContainer
    override fun onCreate() {
        super.onCreate()
        container = ReInventRepository(this)
    }
}