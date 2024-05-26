package com.example.delivery_service.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.delivery_service.models.DeliveryDepartment
import com.example.delivery_service.repository.DataRepository

class DepartmentViewModel : ViewModel() {
    var departmentList: LiveData<List<DeliveryDepartment>> = DataRepository.getInstance().departments
    var department: DeliveryDepartment? = null


    init {
        DataRepository.getInstance().currentDeliveryDepartment.observeForever { dep ->
            department = dep ?: DeliveryDepartment()
        }
    }

    fun deleteDepartment(department: DeliveryDepartment) {
        DataRepository.getInstance().deleteDeliveryDepartment(department)
    }

    fun appendDepartment(name: String) {
        val department = DeliveryDepartment()
        department.deliveryDepartmentName = name
        DataRepository.getInstance().addDeliveryDepartment(department)
    }

    fun updateDepartment(department: DeliveryDepartment) {
        DataRepository.getInstance().editDeliveryDepartment(department)
    }

    fun setCurrentDepartment(department: DeliveryDepartment) {
        DataRepository.getInstance().setCurrentDeliveryDepartment(department)
    }

    fun setCurrentDepartment(position: Int) {
        if ((departmentList.value?.size ?: 0) > position)
            departmentList.value?.let { DataRepository.getInstance().setCurrentDeliveryDepartment(it.get(position)) }
    }

    val getDepartmentListPosition get() = departmentList.value?.indexOfFirst { it.id == department?.id } ?: -1
}