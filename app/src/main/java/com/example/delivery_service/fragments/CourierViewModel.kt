package com.example.delivery_service.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment
import com.example.delivery_service.repository.DataRepository

class CourierViewModel : ViewModel() {

    lateinit var department: DeliveryDepartment

    var courierList: MutableLiveData<List<Courier>> = MutableLiveData()
    private var _courier: Courier? = null
    val couier get() = _courier

    private val courierListObserver = Observer<List<Courier>> { list ->
        courierList.postValue(
            list.filter {it.deliveryDepartmentID == department?.id} as MutableList<Courier>
        )
    }

    fun set_Department(department: DeliveryDepartment){
        this.department = department
        DataRepository.getInstance().couriers.observeForever(courierListObserver)
        DataRepository.getInstance().currentCouriers.observeForever {
            _courier = it
        }
    }

    fun sortedByName(){
        val sortedOrders = courierList.value?.sortedByDescending { it.getFIO() }
        courierList.postValue(sortedOrders!!)
    }

    fun sortedByUndeliveredOrders(){
        val sortedOrders = courierList.value?.sortedBy { it.undeliveredOrdersCount }
        courierList.postValue(sortedOrders!!)
    }


    fun deleteCourier() {
        if (couier != null) {
            DataRepository.getInstance().deleteCourier(couier!!)
        }
    }

    fun setCurrentCourier(courier: Courier) {
        DataRepository.getInstance().setCurrentCourier(courier)
    }
}