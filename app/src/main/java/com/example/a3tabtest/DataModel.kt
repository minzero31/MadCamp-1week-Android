// DataModel.kt
package com.example.a3tabtest

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val phoneNumber: String,
    val address: String
)
