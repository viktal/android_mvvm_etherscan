package main.src.etherscan.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import main.src.etherscan.TypeTrans
import main.src.etherscan.data.models.TransactionModel
import main.src.etherscan.data.repositories.EthplorerRepository

class TransactionViewModel : ViewModel() {
    private var address: String? = null
    private var typeTransaction: TypeTrans? = null
    private var transactionAddress: String? = null
    private var rate: Double? = null
    private var timestamp: Int? = null

    private var pagedTransactions: Flow<PagingData<TransactionModel>>? = null
    private val repo = EthplorerRepository()

    fun fetchTransactions(
        address: String, typeTransaction: TypeTrans, transactionAddress: String,
        rate: Double, timestamp: Int
    ): Flow<PagingData<TransactionModel>> {
        if (
            this.address == address && this.typeTransaction == typeTransaction &&
            this.transactionAddress == transactionAddress && this.rate == rate &&
            this.timestamp == timestamp
        )
            return pagedTransactions!!

        this.address = address
        this.typeTransaction = typeTransaction
        this.transactionAddress = transactionAddress
        this.rate = rate
        this.timestamp = timestamp

        pagedTransactions = repo.getPagedTransactionsStream(
            address, typeTransaction, transactionAddress, rate, timestamp
        ).cachedIn(viewModelScope)

        return pagedTransactions!!
    }
}
