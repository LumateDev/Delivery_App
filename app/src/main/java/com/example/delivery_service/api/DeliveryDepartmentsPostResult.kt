package com.example.delivery_service.api

import com.example.delivery_service.models.DeliveryDepartment
import com.google.gson.annotations.SerializedName

class DeliveryDepartmentsPostResult {
    @SerializedName("") lateinit var resultStr : DeliveryDepartment
}