package com.example.delivery_service.api

import com.example.delivery_service.models.DeliveryDepartment
import com.google.gson.annotations.SerializedName

data class DeliveryDepartmentsPost (
    @SerializedName("action") var action : Int,
    @SerializedName("department") val deliveryDepartment : DeliveryDepartment
)