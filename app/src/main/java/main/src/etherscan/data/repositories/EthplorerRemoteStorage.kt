package main.src.etherscan.data.repositories

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.models.EtherTransModel
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.models.ListTokenTransModel
import main.src.etherscan.data.models.PriceJsonAdapter
import main.src.etherscan.data.models.TokenDetailsModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.models.TransactionListModel
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await

class EthplorerRemoteStorage {
    private val url = "https://api.ethplorer.io"
    val apiType = "&type=transfer"
    val apiLimit = "&limit=10"
    private val client = OkHttpClient()
    private val normaliser = NormalizeData()

    suspend fun getAddressInfo(address: String): TokensListModel {
        val apiShow = "&showETHTotals=true"
        val methodURL = "$url/getAddressInfo/$address$apiShow"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(PriceJsonAdapter())
            .build()
        val adapter = moshi.adapter<AddressInfoModel>(AddressInfoModel::class.java)
        val clearResult = adapter.fromJson(json)

        return normaliser.normalizeTokens(clearResult!!)
    }

    suspend fun getEtherTrans(address: String): TransactionListModel {
        val apiLimit = "&limit=10"
        val methodURL = "$url/getAddressTransactions/$address$apiLimit"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(
            MutableList::class.java,
            EtherTransModel::class.java
        )
        val adapter = moshi.adapter<List<EtherTransModel>>(type)
        val clearResult = adapter.fromJson(json)

        return normaliser.normalizeEthTrans(clearResult!!)
    }

    suspend fun getTokenTrans(address: String, transAddress: String): TransactionListModel {

        val methodURL = "$url/getAddressHistory/$address&token=$transAddress$apiType$apiLimit"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter<ListTokenTransModel>(ListTokenTransModel::class.java)
        val clearResult = adapter.fromJson(json)

        return normaliser.normalizeTokenTrans(clearResult!!)
    }

    suspend fun getHistoryGroupedEth(): HistoryGroupEth {

        val methodURL = "$url/getTokenHistoryGrouped?apiKey=ethplorer.widget&cap=true"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter<HistoryGroupEth>(HistoryGroupEth::class.java)
        val clearResult = adapter.fromJson(json)

        return clearResult!!
    }

    suspend fun getTxInfo(transHash: String): TokenDetailsModel {
        val methodURL = "$url/getTxInfo/$transHash"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val clearResult = Moshi.Builder().build()
            .adapter<TokenDetailsModel>(TokenDetailsModel::class.java)
            .fromJson(json)

        return clearResult!!
    }
}
