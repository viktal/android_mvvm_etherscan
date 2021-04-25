package main.src.etherscan.data.repositories

import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.models.EtherTransModel
import main.src.etherscan.data.models.ListEtherTransModel
import main.src.etherscan.data.models.TokensListModel

class EthplorerRepository {
    val network = EthplorerRemoteStorage()
    private val apiKey = "?apiKey=EK-3HTzj-zcxPo7d-qCssY"

    suspend fun getAddressInfo(address: String): TokensListModel {
        return network.getAddressInfo(address+apiKey)
    }

    suspend fun getEtherTrans(address: String): List<EtherTransModel> {
        return network.getEtherTrans(address+apiKey)!!
    }
}
