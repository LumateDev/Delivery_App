package com.example.delivery_service.api

import com.example.delivery_service.models.Courier
import com.google.gson.annotations.SerializedName

class CouriersResponse {
    @SerializedName("couriers") lateinit var elems : List<Courier>
}