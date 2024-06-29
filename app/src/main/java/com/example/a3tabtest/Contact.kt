package com.example.a3tabtest


// Contact.kt
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Contact(
    @SerialName("name") val name: String,
    @SerialName("phoneNumber") val phoneNumber: String
)
