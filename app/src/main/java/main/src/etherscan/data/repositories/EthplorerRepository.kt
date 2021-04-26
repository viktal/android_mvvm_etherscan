package main.src.etherscan.data.repositories

import main.src.etherscan.TypeTrans
import main.src.etherscan.data.models.EtherTransModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.models.*

class EthplorerRepository {
    val network = EthplorerRemoteStorage()
    private val apiKey = "?apiKey=EK-3HTzj-zcxPo7d-qCssY"

    suspend fun getAddressInfo(address: String): TokensListModel {
        return network.getAddressInfo(address + apiKey)
    }

    suspend fun getTrans(address: String, typeTrans: TypeTrans, transAddress: String): TransactionListModel {
        if (typeTrans == TypeTrans.ETHER) {
            return network.getEtherTrans(address + apiKey)!!
        } else {
            return network.getTokenTrans(address + apiKey, transAddress)!!
        }

    }
}
