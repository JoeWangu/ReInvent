package com.saddict.reinvent.model.remote

import com.fasterxml.jackson.annotation.JsonProperty
import androidx.annotation.Keep

@Keep
data class User(
    val password: String,
    val username: String
)

@Keep
data class UserResponse(
    @JsonProperty("user")
    val user: String,
    @JsonProperty("token")
    val token: String,
    @JsonProperty("created")
    val created: Boolean
)