package com.example.delivery_service.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment

import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface DeliveryAppDAO {

    @Query("SELECT * FROM deliveryDepartments ORDER BY deliveryDepartmentName")
    fun getAllDeliveryDepartments(): Flow<List<DeliveryDepartment>>

    @Insert(entity = DeliveryDepartment::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryDepartments(deliveryDepartment: DeliveryDepartment)

    @Update(entity = DeliveryDepartment::class)
    suspend fun updateDeliveryDepartments(deliveryDepartment: DeliveryDepartment)

    @Insert(entity = DeliveryDepartment::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListDeliveryDepartments(deliveryDepartment: List<DeliveryDepartment>)

    @Delete(entity = DeliveryDepartment::class)
    suspend fun deleteDeliveryDepartment(deliveryDepartment: DeliveryDepartment)

    @Query("DELETE FROM deliveryDepartments")
    suspend fun deleteAllDeliveryDepartments()

    @Query("SELECT * FROM couriers")
    fun getAllCouriers(): Flow<List<Courier>>

    @Query("SELECT * FROM couriers WHERE deliveryDepartmentID=:id")
    fun getAllCouriersByDepartment(id: UUID): Flow<List<Courier>>

    @Insert(entity = Courier::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourier(courier: Courier)

    @Update(entity = Courier::class)
    suspend fun updateCourier(courier: Courier)

    @Insert(entity = Courier::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCouriers(courier: List<Courier>)

    @Delete(entity = Courier::class)
    suspend fun deleteCourier(courier: Courier)

    @Query("DELETE FROM couriers")
    suspend fun deleteAllCouriers()

}