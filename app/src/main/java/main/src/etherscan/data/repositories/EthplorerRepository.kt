package main.src.etherscan.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import main.src.etherscan.TypeTrans
import main.src.etherscan.data.TransactionsPagingSource
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.models.TokenDetailsModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.models.TransactionListModel
import main.src.etherscan.data.models.TransactionModel

class EthplorerRepository {
    private val network = EthplorerRemoteStorage()
    private val apiKey = "?apiKey=EK-3HTzj-zcxPo7d-qCssY"

    suspend fun getAddressInfo(address: String): TokensListModel {
        return network.getAddressInfo(address + apiKey)
    }

    suspend fun getTrans(
        address: String,
        typeTrans: TypeTrans,
        transAddress: String,
        rate: Double,
        timestamp: Int
    ): TransactionListModel {
        return if (typeTrans == TypeTrans.ETHER) {
            network.getEtherTrans(address + apiKey, rate, timestamp)
        } else {
            network.getTokenTrans(address + apiKey, transAddress, timestamp)
        }
    }

    fun getPagedTransactionsStream(
        address: String,
        typeTrans: TypeTrans,
        transAddress: String,
        rate: Double,
        timestamp: Int
    ): Flow<PagingData<TransactionModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                TransactionsPagingSource(
                    address,
                    typeTrans,
                    transAddress,
                    rate,
                    timestamp,
                    this
                )
            }
        ).flow
    }

    suspend fun getHistoryGrouped(): HistoryGroupEth {
        return network.getHistoryGroupedEth()
    }

    suspend fun getTransDetails(trans_hash: String): TokenDetailsModel {
        return network.getTxInfo(trans_hash + apiKey)
    }
}
