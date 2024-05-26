package com.example.delivery_service.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID


@Entity(
    tableName = "deliveryDepartments", //fsdfsd
    indices = [Index("id")] //fsdfsd
)

data class DeliveryDepartment(
    @SerializedName ("external_id")
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    @SerializedName ("department_name")
    var deliveryDepartmentName : String = ""
)
