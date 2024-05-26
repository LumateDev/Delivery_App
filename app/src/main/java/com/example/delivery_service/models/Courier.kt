package com.example.delivery_service.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID


@Entity(
    tableName = "couriers",
    indices = [Index("id"), Index("deliveryDepartmentID")], // аывавыа
    foreignKeys = [
        ForeignKey(
            entity = DeliveryDepartment::class,
            parentColumns = ["id"],
            childColumns = ["deliveryDepartmentID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Courier(
    @PrimaryKey var id : UUID = UUID.randomUUID(),
    var deliveryDepartmentID : UUID? = null,
    var undeliveredOrdersCount : Int = 0, // количество недоставленных заказов
    var firstName : String = "", // Имя
    var middleName : String = "", // Отчество
    var lastName : String = "", // Фамилия
    var email : String = "", // email
    var phoneNumber : String = "", // Телефон
    var startDate : Long = Date().time, // Дата начала работы
    var vehicleType : String = "",  // Тип транспорта
    var licenseNumber : String = "" //Номер лицензии
){

}
