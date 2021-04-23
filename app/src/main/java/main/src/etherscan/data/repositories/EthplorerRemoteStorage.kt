package main.src.etherscan.data.repositories

import com.beust.klaxon.Klaxon
import main.src.etherscan.data.models.AddressInfoModel
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await

class EthplorerRemoteStorage {
    private val url = "https://api.ethplorer.io"
    private val client = OkHttpClient()


    suspend fun getAddressInfo(address: String) : AddressInfoModel? {
        val methodURL = "$url/getAddressInfo/$address"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()

        return Klaxon()
            .parse<AddressInfoModel>(result.body!!.string())
    }
}

