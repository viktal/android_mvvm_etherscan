package main.src.etherscan.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import main.src.etherscan.TypeTrans
import main.src.etherscan.data.models.TransactionModel
import main.src.etherscan.data.repositories.EthplorerRepository

class TransactionsPagingSource(
    private val address: String,
    private val typeTrans: TypeTrans,
    private val transAddress: String,
    private val rate: Double,
    private val initTimestamp: Int,
    private val service: EthplorerRepository
) : PagingSource<Int, TransactionModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionModel> {
        val timestamp = params.key ?: initTimestamp
        return try {
            val response = service.getTrans(address, typeTrans, transAddress, rate, timestamp)
            var nextKey: Int? = null
            if (!response.transaction.isEmpty()) {
                nextKey = response.transaction.last().timestamp
                nextKey = if (nextKey == timestamp) null else nextKey
            }
            LoadResult.Page(
                data = response.transaction,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TransactionModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
