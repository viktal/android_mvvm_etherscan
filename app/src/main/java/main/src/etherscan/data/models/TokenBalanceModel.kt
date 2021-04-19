package main.src.etherscan.data.models

data class TokenBalanceModel(
    val tokenInfo: TokenInfoModel,
    val balance: Int,
    val totalIn: Int,
    val totalOut: Int
)
