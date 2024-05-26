package com.example.delivery_service.api

import com.google.gson.annotations.SerializedName

class PostResultAPI {
    @SerializedName("result") lateinit var resultString: String
}