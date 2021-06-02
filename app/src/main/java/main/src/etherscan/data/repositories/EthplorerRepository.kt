package main.src.etherscan.data.repositories

import main.src.etherscan.TypeTrans
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.models.TokenDetailsModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.models.TransactionListModel

class EthplorerRepository {
    private val network = EthplorerRemoteStorage()
    private val apiKey = "?apiKey=EK-3HTzj-zcxPo7d-qCssY"

    suspend fun getAddressInfo(address: String): TokensListModel {
        return network.getAddressInfo(address + apiKey)
    }

    suspend fun getTrans(address: String, typeTrans: TypeTrans, transAddress: String): TransactionListModel {
        return if (typeTrans == TypeTrans.ETHER) {
            network.getEtherTrans(address + apiKey)
        } else {
            network.getTokenTrans(address + apiKey, transAddress)
        }
    }

    suspend fun getHistoryGrouped(): HistoryGroupEth {
        return network.getHistoryGroupedEth()
    }

    suspend fun getTransDetails(trans_hash: String): TokenDetailsModel {
        return network.getTxInfo(trans_hash + apiKey)
    }
}
