package main.src.etherscan.data.repositories

import main.src.etherscan.data.models.AddressInfoModel

class EthplorerRepository {
    val network = EthplorerRemoteStorage()

    suspend fun getAddressInfo(address: String): AddressInfoModel? {
        return network.getAddressInfo(address)
    }
}
