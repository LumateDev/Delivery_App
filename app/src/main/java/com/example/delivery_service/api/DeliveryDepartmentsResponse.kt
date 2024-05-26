package com.example.delivery_service.api

import com.example.delivery_service.models.DeliveryDepartment
import com.google.gson.annotations.SerializedName

class DeliveryDepartmentsResponse {
    @SerializedName("departments") lateinit var elems : List<DeliveryDepartment>
}