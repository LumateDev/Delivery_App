package com.example.delivery_service.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
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
    @SerializedName("external_id")
    @PrimaryKey var id : UUID = UUID.randomUUID(),
    @SerializedName("department")
    var deliveryDepartmentID : UUID? = null,
    @SerializedName("undelivered_orders_count")
    var undeliveredOrdersCount : Int = 0, // количество недоставленных заказов
    @SerializedName("first_name")
    var firstName : String = "", // Имя
    @SerializedName("middle_name")
    var middleName : String = "", // Отчество
    @SerializedName("last_name")
    var lastName : String = "", // Фамилия
    @SerializedName("email")
    var email : String = "", // email
    @SerializedName("phone_number")
    var phoneNumber : String = "", // Телефон
    @SerializedName("start_date")
    var startDate : Long = Date().time, // Дата начала работы
    @SerializedName("vehicle_type")
    var vehicleType : Int = 0,  // Тип транспорта
    @SerializedName("license_number")
    var licenseNumber : String = "" //Номер лицензии
){
    fun getFIO () = "$lastName $firstName $middleName"


}
