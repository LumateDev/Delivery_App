package com.example.delivery_service.fragments

import androidx.lifecycle.ViewModel
import com.example.delivery_service.models.Courier
import com.example.delivery_service.repository.DataRepository

import java.util.Date

class CourierEditViewModel : ViewModel() {


    val deliveryDepartment get() = DataRepository.getInstance().currentDeliveryDepartment.value

    private var _courier: Courier? = null
    var courier: Courier?
        get() = _courier
        set(value) {
            this._courier = value
        }

    var date: Long = Date().time

    fun appendCourier(courierLastName: String, courierFistName: String, courierMiddleName: String, courierPhone : String,
                      courierEmail: String, courierVehicleType: Int, courierStartDate: Long, courierLicenseNumber: String, courierUndeliveredOrdersCount : Int ) {
        val newCourier = Courier()
        newCourier.deliveryDepartmentID = deliveryDepartment!!.id
        newCourier.lastName = courierLastName
        newCourier.firstName = courierFistName
        newCourier.middleName = courierMiddleName
        newCourier.phoneNumber = courierPhone
        newCourier.vehicleType = courierVehicleType
        newCourier.undeliveredOrdersCount = courierUndeliveredOrdersCount
        newCourier.startDate = courierStartDate
        newCourier.licenseNumber = courierLicenseNumber
        newCourier.email = courierEmail
        DataRepository.getInstance().addCourier(newCourier)
    }

    fun updateCourier(courierLastName: String, courierFistName: String, courierMiddleName: String, courierPhone : String,
                      courierEmail: String, courierVehicleType: Int, courierStartDate: Long, courierLicenseNumber: String, courierUndeliveredOrdersCount : Int ) {
        if (_courier != null) {

            _courier!!.lastName = courierLastName
            _courier!!.firstName = courierFistName
            _courier!!.middleName = courierMiddleName
            _courier!!.phoneNumber = courierPhone
            _courier!!.vehicleType = courierVehicleType
            _courier!!.undeliveredOrdersCount = courierUndeliveredOrdersCount
            _courier!!.startDate = courierStartDate
            _courier!!.licenseNumber = courierLicenseNumber
            _courier!!.email = courierEmail
            DataRepository.getInstance().editCourier(_courier!!)
        }
    }

}