package main.src.etherscan.data.models


data class TokenBalanceModel(
    val tokenInfo: TokenInfoModel,
    val balance: Double,
    val totalIn: Int,
    val totalOut: Int
)
