package com.example.delivery_service.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface DeliveryAPI {
    @GET("delivery_departments")
    fun getDeliveryDepartments() : Call <DeliveryDepartmentsResponse>

    @Headers("Content-Type: application/json")
    @POST("delivery_departments")
    fun postDeliveryDepartments(@Body postDeliveryDepartments:DeliveryDepartmentsPost): Call<DeliveryDepartmentsPostResult>

    @GET("couriers")
    fun getCouriers(): Call<CouriersResponse>

    @Headers("Content-Type: application/json")
    @POST("couriers")
    fun postCouriers(@Body postCourier: CouriersPost): Call<CouriersPostResult>


}