package main.src.etherscan.data.repositories

import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.models.TokensListModel

class EthplorerRepository {
    val network = EthplorerRemoteStorage()
    private val apiParams = "?apiKey=EK-3HTzj-zcxPo7d-qCssY&showETHTotals=true"

    suspend fun getAddressInfo(address: String): TokensListModel {
        return network.getAddressInfo(address+apiParams)
    }
}
