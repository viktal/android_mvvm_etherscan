package main.src.etherscan.data.models

data class TokenInfoModel(
    val address: String,
    val totalSupply: String,
    val name: String,
    val symbol: String,
    val decimals: String,
    val price: PriceModel?,
    val owner: String,
    val countOps: Int? = null,
    val transfersCount: Int? = null,
    val holdersCount: Int,
    val issuancesCount: Int,
    val lastUpdated: Int,
    val image: String = ""
)
