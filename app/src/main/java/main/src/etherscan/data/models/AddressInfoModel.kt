package main.src.etherscan.data.models

import com.beust.klaxon.JsonArray

data class AddressInfoModel(
    // val address: String,
    // val ETH: ETHModel,
    // val contractInfo: ContractInfoModel,
    // val tokenInfo: TokenInfoModel,
    val tokens: MutableList<TokenBalanceModel>
    // val countTxs: Int
)
