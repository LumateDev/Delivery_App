package com.example.delivery_service.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(
    tableName = "deliveryDepartments", //fsdfsd
    indices = [Index("id")] //fsdfsd
)

data class DeliveryDepartment(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var deliveryDepartmentName : String = ""
)
