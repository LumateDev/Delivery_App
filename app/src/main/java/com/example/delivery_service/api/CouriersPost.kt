package com.example.delivery_service.api

import androidx.room.Index
import com.example.delivery_service.models.Courier
import com.google.gson.annotations.SerializedName

data class CouriersPost (
    @SerializedName ("action") val action : Int,
    @SerializedName("courier") var couriers: Courier,
)