package main.src.etherscan.data.models
import com.beust.klaxon.Json

data class TokenInfoModel(
    val address: String,
    val totalSupply: String,
    val name: String,
    val symbol: String,
    val decimals: String,
    @Json(ignored = true)
    val price: PriceModel? = null,
    val owner: String,
    val countOps: Int? = null,
    val transfersCount: Int? = null,
    val holdersCount: Int,
    val issuancesCount: Int,
    val lastUpdated: Int
)
