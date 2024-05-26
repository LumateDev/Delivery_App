package com.example.delivery_service

import android.app.Application
import android.content.Context
import com.example.delivery_service.repository.DataRepository

class ApplicationDelivery : Application() {


    init {
        instance = this
    }

    companion object {
        private var instance: ApplicationDelivery? = null
        val context
            get() = applicationContext()
        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        DataRepository.getInstance().fetchDeliveryDepartments()

    }

}