package com.example.delivery_service

import android.app.Application
import android.content.Context
import android.provider.ContactsContract.Data
import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment
import com.example.delivery_service.repository.APPEND
import com.example.delivery_service.repository.DataRepository
import java.util.Date
import java.util.UUID

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
        /*val deb = DeliveryDepartment()
        deb.deliveryDepartmentName = "Херня"
        DataRepository.getInstance().addDeliveryDepartment(deb)*/
//        val debC = Courier()
//        debC.deliveryDepartmentID = UUID.fromString("95bf321e-0916-43f5-b35c-866b9f73ecbd")
//        debC.firstName = "Aboba"
//        debC.lastName = "Victorovich"
//        debC.middleName = "TANKIST"
//        debC.email = "aboab@mail.ru"
//        debC.licenseNumber = "X123-gGD"
//        debC.vehicleType = "car"
//        debC.undeliveredOrdersCount = 2
//        debC.startDate = Date().time
//        debC.phoneNumber = "XXX-3333"
//        DataRepository.getInstance().addCourier(debC)





    }

}