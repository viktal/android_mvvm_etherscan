package main.src.etherscan.data.models

data class TokensListModel(
    val tokens: MutableList<MainPageTokenModel>,
    val totalSum: Number,
    val dailyMoney: Number,
    val growth: Boolean
)
