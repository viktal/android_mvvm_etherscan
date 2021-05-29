package main.src.etherscan.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EtherTransModel(
    val timestamp: Int? = -1,
    val from: String,
    val to: String,
    val hash: String,
    val value: Double,
    val input: String,
    val success: Boolean
)
