package main.src.etherscan.data.repositories

import android.util.JsonReader
import android.util.Log
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import main.src.etherscan.data.models.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import java.io.StringReader
import java.util.regex.Pattern
import kotlin.math.pow


class EthplorerRemoteStorage {
    private val url = "https://api.ethplorer.io"
    private val client = OkHttpClient()

    suspend fun getAddressInfo(address: String): TokensListModel {
        val methodURL = "$url/getAddressInfo/$address"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val clearResult = Klaxon()
                .fieldConverter(PriceModelOrFalse::class, PriceModelOrFalseConverter())
                .parse<AddressInfoModel>(json)


        return normalizeTokens(clearResult!!)
    }

    fun Double.roundTo(n : Int) : String {
        return "%.${n}f".format(this)
    }

    fun createSingleToken(item: TokenBalanceModel): MainPageTokenModel {
        val itemInfo = item.tokenInfo;

        val itemBalance = item.balance / 10.0.pow(itemInfo.decimals.toDouble())
        var returningToken: MainPageTokenModel
        if (item.tokenInfo.price != null) {
            val itemPrice = itemInfo.price

            val tmpPrice = itemBalance * itemPrice!!.rate
            returningToken = MainPageTokenModel(
                    address = itemInfo.address,
                    symbol = itemInfo.symbol,
                    name = itemInfo.name,
                    balance = itemBalance.roundTo(2),
                    price = tmpPrice.roundTo(2),
                    logo = itemInfo.image,
                    rate = itemPrice.rate.roundTo(2),
                    dif = itemPrice.diff.roundTo(2)
            )
        } else {
            returningToken = MainPageTokenModel(
                    address = itemInfo.address,
                    symbol = itemInfo.symbol,
                    name = itemInfo.name,
                    balance = itemBalance.roundTo(2),
                    price = "0",
                    logo = itemInfo.image,
                    rate = "0",
                    dif = "0,00"
            )
        }
        return returningToken
    }

    fun normalizeTokens(someTokens:  AddressInfoModel): TokensListModel {
        var totalSum = 0.0
        var dailyMoney = 0.0
        val tokensForRender: MutableList<MainPageTokenModel> = ArrayList()
        val ethBalance = someTokens.ETH.balance * someTokens.ETH.price.rate;

        val ethData: MainPageTokenModel = MainPageTokenModel(
                address = someTokens.address,
                symbol = "ETH",
                name = "Ethereum",
                balance = someTokens.ETH.balance.roundTo(2),
                price = ethBalance.roundTo(2),
                logo = "",
                rate = someTokens.ETH.price.rate.roundTo(2),
                dif = someTokens.ETH.price.diff.roundTo(2)
        )
        tokensForRender.add(ethData)

        dailyMoney += (ethBalance * someTokens.ETH.price.diff) / (100 + someTokens.ETH.price.diff)
        totalSum += ethBalance

        someTokens.tokens.forEach {
            if (it.tokenInfo.price != null) {
                val itemBalance = it.balance / 10.0.pow(it.tokenInfo.decimals.toDouble())
                val ItemBalanceDol = itemBalance * it.tokenInfo.price.rate
                totalSum += ItemBalanceDol
                dailyMoney += ItemBalanceDol * it.tokenInfo.price.diff / (100 + it.tokenInfo.price.diff)
                tokensForRender.add(createSingleToken(it))
            }
        }

        return TokensListModel(
                tokens = tokensForRender,
                totalSum = totalSum,
                dailyMoney = dailyMoney
        )
    }
}
