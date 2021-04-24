package main.src.etherscan.data.repositories

import main.src.etherscan.data.models.AddressInfoModel

class EthplorerRepository {
    val network = EthplorerRemoteStorage()
    private val apiParams = "?apiKey=EK-3HTzj-zcxPo7d-qCssY&showETHTotals=true"

    suspend fun getAddressInfo(address: String): AddressInfoModel? {
        return network.getAddressInfo(address+apiParams)
    }
}
