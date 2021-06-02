package main.src.etherscan.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenTransModel(
    val timestamp: Int,
    val transactionHash: String,
    val tokenInfo: TokenInfoModel,
    val type: String,
    val from: String,
    val to: String,
    val value: String
)

@JsonClass(generateAdapter = true)
data class ListTokenTransModel(
    val operations: MutableList<TokenTransModel>
)
