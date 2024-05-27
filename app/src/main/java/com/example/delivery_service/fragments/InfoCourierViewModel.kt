package com.example.delivery_service.fragments

import androidx.lifecycle.ViewModel
import com.example.delivery_service.repository.DataRepository

class InfoCourierViewModel : ViewModel() {
    var courier = DataRepository.getInstance().currentCouriers
    var deliveryDepartment = DataRepository.getInstance().currentDeliveryDepartment
}