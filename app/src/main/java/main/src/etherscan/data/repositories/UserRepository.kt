package main.src.etherscan.data.repositories

import main.src.etherscan.data.models.AddressInfoModel

class UserRepository() {
    val network = EthplorerRemoteStorage()
    private val apiParams = "?apiKey=EK-3HTzj-zcxPo7d-qCssY&showETHTotals=true"

    suspend fun loginByMnemonic(mnemonic: String): AddressInfoModel? {
        return network.getAddressInfo(apiParams)
    }
    suspend fun  loginByAddress(address: String): AddressInfoModel? {
        return network.getAddressInfo(address+apiParams)
    }

}
