package com.example.delivery_service

import com.example.delivery_service.models.Courier

interface ActivityInterface {
    fun updateTitle(newTitle: String)
    fun setFragment(fragmentId: Int, courier: Courier? = null)
}