package main.src.etherscan.data.models

import com.beust.klaxon.Json

data class AddressInfoModel(
    val address: String,
    val ETH: ETHModel,
    // @Json(ignored = true)
    // val contractInfo: ContractInfoModel?,
    // val tokenInfo: TokenInfoModel,
    val tokens: MutableList<TokenBalanceModel>,
    val countTxs: Int
)
