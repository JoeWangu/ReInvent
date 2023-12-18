package com.saddict.reinvent

import android.app.Application
import com.saddict.reinvent.products.data.manager.ReInventContainer
import com.saddict.reinvent.products.data.manager.ReInventRepository

class ReInventApplication: Application() {
    lateinit var container: ReInventContainer
    override fun onCreate() {
        super.onCreate()
        container = ReInventRepository(this)
    }
}