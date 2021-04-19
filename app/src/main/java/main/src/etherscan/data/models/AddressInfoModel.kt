package main.src.etherscan.data.models

data class AddressInfoModel(
    val address: String,
    val ETH: ETHModel,
    val contractInfo: ContractInfoModel,
    val tokenInfo: TokenInfoModel,
    val tokens: MutableList<TokenBalanceModel>,
    val countTxs: Int
)