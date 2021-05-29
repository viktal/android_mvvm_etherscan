package main.src.etherscan.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenDetailsModel(
    val hash: String,
    val timestamp: Int,
    val blockNumber: Int,
    val confirmations: Int,
    val success: Boolean,
    val from: String,
    val to: String,
    val value: Double,
    val input: String,
    val gasLimit: Int,
    val gasUsed: Int,
    val logs: Array<Any>
)
