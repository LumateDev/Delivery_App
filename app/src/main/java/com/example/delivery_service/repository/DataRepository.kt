package com.example.delivery_service.repository

import android.icu.text.Edits
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.delivery_service.ApplicationDelivery
import com.example.delivery_service.api.Connection
import com.example.delivery_service.api.CouriersPost
import com.example.delivery_service.api.CouriersPostResult
import com.example.delivery_service.api.CouriersResponse
import com.example.delivery_service.api.DeliveryAPI
import com.example.delivery_service.api.DeliveryDepartmentsPost
import com.example.delivery_service.api.DeliveryDepartmentsPostResult
import com.example.delivery_service.api.DeliveryDepartmentsResponse
import com.example.delivery_service.database.InternalDB
import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

const val API = "API_TAG"
const val APPEND = 11
const val UPDATE = 12
const val DELETE = 13


class DataRepository private constructor() {

    companion object {
        private var INSTANCE: DataRepository? = null
        fun getInstance(): DataRepository {
            if (INSTANCE == null) {
                INSTANCE = DataRepository()
            }
            return INSTANCE ?: throw IllegalStateException("DataRepository is not created.")
        }
    }

    private val localeDB by lazy {

        DBRepository(InternalDB.getDatabase(ApplicationDelivery.context).dao())
    }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    fun onDestroy() {
        myCoroutineScope.cancel()
    }

    private var deliveryAPI = Connection.getClient().create(DeliveryAPI::class.java)

    val departments: LiveData<List<DeliveryDepartment>> = localeDB.getAllDeliveryDepartments().asLiveData()
    val couriers: LiveData<List<Courier>> = localeDB.getAllCouriers().asLiveData()
    var currentDeliveryDepartment: MutableLiveData<DeliveryDepartment> = MutableLiveData()
    var currentCouriers: MutableLiveData<Courier> = MutableLiveData()

    fun setCurrentDeliveryDepartment(_deparment : DeliveryDepartment) {
        currentDeliveryDepartment.postValue(_deparment)
    }

    fun setCurrentCourier(_courier: Courier) {
        currentCouriers.postValue(_courier)
    }

    fun fetchDeliveryDepartments() {
        deliveryAPI.getDeliveryDepartments().enqueue(object : Callback<DeliveryDepartmentsResponse> {
            override fun onFailure(call: Call<DeliveryDepartmentsResponse>, t: Throwable) {
                Log.d(API, "Ошибка получения отделов доставки")
            }

            override fun onResponse(
                call: Call<DeliveryDepartmentsResponse>,
                responce: Response<DeliveryDepartmentsResponse>
            ) {
                val departments = responce.body()
                val elements = departments?.elems ?: emptyList()
                Log.d(API, "Получили отделы доставки, размер=${elements.size}")
                myCoroutineScope.launch {
                    localeDB.deleteAllDeliveryDepartments()
                    for (elem in elements) {
                        localeDB.insertDeliveryDepartments(elem)
                    }

                    fetchCouriers()
                }
            }
        })
    }

    private fun fetchCouriers() {
        deliveryAPI.getCouriers().enqueue(object : Callback<CouriersResponse>{
            override fun onResponse(call: Call<CouriersResponse>, responce: Response<CouriersResponse>) {
                val courier = responce.body()
                val elements = courier?.elems ?: emptyList()
                Log.d(API, "Получили курьеров, количество =${elements.size}")
                myCoroutineScope.launch {
                    localeDB.deleteAllCouriers()
                    for (elem in elements) {
                        localeDB.insertCourier(elem)
                    }

                }
            }

            override fun onFailure(call: Call<CouriersResponse>, t: Throwable) {
                Log.d(API, "Ошибка получения списка курьеров")
            }

        })
    }

    private fun postDeliveryDepartments(action: Int, deliveryDepartment: DeliveryDepartment) {
        deliveryAPI.postDeliveryDepartments(DeliveryDepartmentsPost(action, deliveryDepartment)).enqueue(object : Callback<DeliveryDepartmentsPostResult>{
            override fun onResponse(p0: Call<DeliveryDepartmentsPostResult>, p1: Response<DeliveryDepartmentsPostResult>) {
                Log.d(API, "Запрос с отделом доставки = ${deliveryDepartment.deliveryDepartmentName}, отправлен с кодом=${action}}")
                fetchDeliveryDepartments()
            }

            override fun onFailure(p0: Call<DeliveryDepartmentsPostResult>, p1: Throwable) {
                Log.d(API, "Ошибка запроса с кодом=${action}, для отдела доставки= ${deliveryDepartment.deliveryDepartmentName}")
            }

        })
    }

    private fun postCourier(action: Int, courier: Courier) {
        deliveryAPI.postCouriers(CouriersPost(action, courier)).enqueue(object : Callback<CouriersPostResult>{
            override fun onResponse(p0: Call<CouriersPostResult>, p1: Response<CouriersPostResult>) {
                Log.d(API, "Запрос с доставщиком = ${courier.firstName + " " + courier.lastName}, отправлен с кодом=${action}}")
                fetchDeliveryDepartments()
            }

            override fun onFailure(p0: Call<CouriersPostResult>, p1: Throwable) {
                Log.d(API, "Ошибка запроса с кодом=${action}, для доставщика =${courier.firstName + " " + courier.lastName}")
            }

        })
    }

    fun addDeliveryDepartment(deliveryDepartment: DeliveryDepartment) {
        postDeliveryDepartments(APPEND, deliveryDepartment)
    }

    fun editDeliveryDepartment(deliveryDepartment: DeliveryDepartment) {
        postDeliveryDepartments(UPDATE, deliveryDepartment)
    }

    fun deleteDeliveryDepartment(deliveryDepartment: DeliveryDepartment) {
        postDeliveryDepartments(DELETE, deliveryDepartment)
    }

    fun addCourier(courier: Courier) {
        postCourier(APPEND, courier)
    }

    fun editCourier(courier: Courier) {
        postCourier(UPDATE, courier)
    }

    fun deleteCourier(courier: Courier) {
        postCourier(DELETE, courier)
    }

}