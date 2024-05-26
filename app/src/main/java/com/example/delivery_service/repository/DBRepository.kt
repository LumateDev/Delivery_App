package com.example.delivery_service.repository

import com.example.delivery_service.database.DeliveryAppDAO
import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DBRepository (val dao: DeliveryAppDAO): DeliveryAppRepository {
    override fun getAllDeliveryDepartments(): Flow<List<DeliveryDepartment>> = dao.getAllDeliveryDepartments()

    override suspend fun insertDeliveryDepartments(deliveryDepartment: DeliveryDepartment) = dao.insertDeliveryDepartments(deliveryDepartment)

    override suspend fun updateDeliveryDepartments(deliveryDepartment: DeliveryDepartment) = dao.updateDeliveryDepartments(deliveryDepartment)

    override suspend fun insertListDeliveryDepartments(deliveryDepartment: List<DeliveryDepartment>) = dao.insertListDeliveryDepartments(deliveryDepartment)

    override suspend fun deleteDeliveryDepartment(deliveryDepartment: DeliveryDepartment) = dao.deleteDeliveryDepartment(deliveryDepartment)

    override suspend fun deleteAllDeliveryDepartments() = dao.deleteAllDeliveryDepartments()

    override fun getAllCouriers(): Flow<List<Courier>>  = dao.getAllCouriers()

    override fun getAllCouriersByDepartment(id: UUID): Flow<List<Courier>>  = dao.getAllCouriersByDepartment(id)

    override suspend fun insertCourier(courier: Courier) = dao.insertCourier(courier)

    override suspend fun updateCourier(courier: Courier) = dao.updateCourier(courier)

    override suspend fun insertListCouriers(courier: List<Courier>) = dao.insertListCouriers(courier)

    override suspend fun deleteCourier(courier: Courier) = dao.deleteCourier(courier)

    override suspend fun deleteAllCouriers()  = dao.deleteAllCouriers()

}