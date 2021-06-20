package main.src.etherscan.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressInfoModel(
    val address: String,
    val ETH: ETHModel,
    val tokens: MutableList<TokenBalanceModel>? = null,
    val countTxs: Int
)
