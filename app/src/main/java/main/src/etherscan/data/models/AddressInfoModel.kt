package main.src.etherscan.data.models

import com.beust.klaxon.Json

data class AddressInfoModel(
    val address: String,
    val ETH: ETHModel,
    // @Json(ignored = true)
    // val contractInfo: ContractInfoModel?,
    // val tokenInfo: TokenInfoModel,
    // @Json(serializeNull = false)
    val tokens: MutableList<TokenBalanceModel>? = null,
    val countTxs: Int
)
