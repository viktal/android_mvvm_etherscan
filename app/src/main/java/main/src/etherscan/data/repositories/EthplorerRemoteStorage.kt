package main.src.etherscan.data.repositories

import com.beust.klaxon.Klaxon
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList
import kotlin.math.pow
import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.models.EtherTransModel
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.models.ListTokenTransModel
import main.src.etherscan.data.models.MainPageTokenModel
import main.src.etherscan.data.models.PriceModelHistory
import main.src.etherscan.data.models.PriceModelOrFalse
import main.src.etherscan.data.models.PriceModelOrFalseConverter
import main.src.etherscan.data.models.TokenBalanceModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.models.Totals
import main.src.etherscan.data.models.TransactionListModel
import main.src.etherscan.data.models.TransactionModel
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await
import java.io.StringReader

class EthplorerRemoteStorage {
    private val url = "https://api.ethplorer.io"
    val apiType = "&type=transfer"
    val apiLimit = "&limit=10"
    private val client = OkHttpClient()

    suspend fun getAddressInfo(address: String): TokensListModel {
        val apiShow = "&showETHTotals=true"
        val methodURL = "$url/getAddressInfo/$address$apiShow"

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

    suspend fun getEtherTrans(address: String): TransactionListModel {
        val apiLimit = "&limit=10"
        val methodURL = "$url/getAddressTransactions/$address$apiLimit"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val clearResult = Klaxon()
            .parseArray<EtherTransModel>(json)

        return normalizeEthTrans(clearResult!!)
    }

    suspend fun getTokenTrans(address: String, transAddress: String): TransactionListModel {

        val methodURL = "$url/getAddressHistory/$address&token=$transAddress$apiType$apiLimit"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val clearResult = Klaxon()
            .fieldConverter(PriceModelOrFalse::class, PriceModelOrFalseConverter())
            .parse<ListTokenTransModel>(json)

        return normalizeTokenTrans(clearResult!!)
    }

    suspend fun getHistoryGroupedEth() : HistoryGroupEth {
        val methodURL = "$url/getTokenHistoryGrouped?apiKey=ethplorer.widget&cap=true"

        val request = Request.Builder()
            .url(methodURL)
            .build()

        val result = client.newCall(request).await()
        val json = result.body!!.string()

        val klaxon = Klaxon()
        val parsed = klaxon.parseJsonObject(StringReader(json))
        val dataArray = parsed.array<Any>("prices")
        val prices = dataArray?.let { klaxon.parseFromJsonArray<PriceModelHistory>(it) }

        val tmp = parsed.obj("totals")
        val totals = tmp?.let { klaxon.parseFromJsonObject<Totals>(it) }

        return HistoryGroupEth(prices = prices as MutableList<PriceModelHistory>, totals = totals!!)
    }


    fun Double.roundTo(n: Int): String {
        return "%.${n}f".format(this)
    }

    fun normalizeTokenTrans(transactions: ListTokenTransModel): TransactionListModel {
        var newArr: MutableList<TransactionModel> = ArrayList()

        transactions.operations.forEach {
            val dollars = it.value.toDouble() / 10.0.pow(it.tokenInfo.decimals.toDouble())
            if (it.tokenInfo.price != null) {
                val singleTrans = TransactionModel(
                        from = it.from,
                        to = it.to,
                        date = convertDate(it.timestamp),
                        dollars = (dollars).roundTo(2),
                        coins = (dollars * it.tokenInfo.price.rate).roundTo(2),
                        symbol = it.tokenInfo.symbol
                )
                newArr.add(singleTrans)
            }
        }
        return TransactionListModel(
                transaction = newArr,
                prices = null
        )
    }

    fun normalizeEthTrans(transactions: List<EtherTransModel>): TransactionListModel {
        var newArr: MutableList<TransactionModel> = ArrayList()

        transactions.forEach {
            val singleTrans = TransactionModel(
                    from = it.from,
                    to = it.to,
                    date = convertDate(it.timestamp),
                    // TODO
                    dollars = "123",
                    coins = it.value.roundTo(2),
                    symbol = "ETH"
            )
            newArr.add(singleTrans)
        }

        return TransactionListModel(
                transaction = newArr,
                prices = null
        )
    }

    fun convertDate(timestamp: Number): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(timestamp.toLong() * 1000)
        return sdf.format(netDate)
    }

    fun createSingleToken(item: TokenBalanceModel): MainPageTokenModel {
        val itemInfo = item.tokenInfo

        val itemBalance = item.balance / 10.0.pow(itemInfo.decimals.toDouble())
        val returningToken: MainPageTokenModel
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

    fun normalizeTokens(someTokens: AddressInfoModel): TokensListModel {
        var totalSum = 0.0
        var dailyMoney = 0.0
        val tokensForRender: MutableList<MainPageTokenModel> = ArrayList()
        val ethBalance = someTokens.ETH.balance * someTokens.ETH.price.rate

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


        if (someTokens.tokens != null) {
            someTokens.tokens!!.forEach {
                if (it.tokenInfo.price != null) {
                    val itemBalance = it.balance / 10.0.pow(it.tokenInfo.decimals.toDouble())
                    val ItemBalanceDol = itemBalance * it.tokenInfo.price.rate
                    totalSum += ItemBalanceDol
                    dailyMoney += ItemBalanceDol * it.tokenInfo.price.diff / (100 + it.tokenInfo.price.diff)
                    tokensForRender.add(createSingleToken(it))
                }
            }
        }


        return TokensListModel(
                tokens = tokensForRender,
                totalSum = totalSum,
                dailyMoney = dailyMoney
        )
    }
}
