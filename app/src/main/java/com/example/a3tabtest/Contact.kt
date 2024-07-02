package com.example.a3tabtest

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Contact(
    @SerialName("name") val name: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("address") val address: String // 주소 필드 추가
)
