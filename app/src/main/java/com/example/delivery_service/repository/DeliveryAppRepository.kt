package com.example.delivery_service.repository

import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeliveryAppRepository {


    fun getAllDeliveryDepartments(): Flow<List<DeliveryDepartment>>
    suspend fun insertDeliveryDepartments(deliveryDepartment: DeliveryDepartment)
    suspend fun updateDeliveryDepartments(deliveryDepartment: DeliveryDepartment)
    suspend fun insertListDeliveryDepartments(deliveryDepartment: List<DeliveryDepartment>)
    suspend fun deleteDeliveryDepartment(deliveryDepartment: DeliveryDepartment)
    suspend fun deleteAllDeliveryDepartments()

    fun getAllCouriers(): Flow<List<Courier>>
    fun getAllCouriersByDepartment(id: UUID): Flow<List<Courier>>
    suspend fun insertCourier(courier: Courier)
    suspend fun updateCourier(courier: Courier)
    suspend fun insertListCouriers(courier: List<Courier>)
    suspend fun deleteCourier(courier: Courier)
    suspend fun deleteAllCouriers()
}