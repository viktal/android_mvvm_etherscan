package main.src.etherscan.data.models

data class TokenTransModel (
    val timestamp: Int,
    val transactionHash: String,
    val tokenInfo: TokenInfoModel,
    val type: String,
    val from: String,
    val to: String,
    val value: String
)

data class ListTokenTransModel (
    val operations: MutableList<TokenTransModel>
)