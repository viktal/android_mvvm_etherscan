package main.src.etherscan.data.repositories

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.pow
import kotlin.math.round
import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.models.EtherTransModel
import main.src.etherscan.data.models.ListTokenTransModel
import main.src.etherscan.data.models.MainPageTokenModel
import main.src.etherscan.data.models.TokenBalanceModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.models.TransactionListModel
import main.src.etherscan.data.models.TransactionModel

class NormalizeData {
    fun Double.roundTo(n: Int): String {
        return "%.${n}f".format(this)
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    fun normalizeTokenTrans(transactions: ListTokenTransModel): TransactionListModel {
        val newArr: MutableList<TransactionModel> = ArrayList()

        transactions.operations.forEach {
            val dollars = it.value.toDouble() / 10.0.pow(it.tokenInfo.decimals.toDouble())
            if (it.tokenInfo.price != null) {
                val singleTrans = TransactionModel(
                    from = it.from,
                    to = it.to,
                    date = convertDate(it.timestamp),
                    timestamp = it.timestamp,
                    dollars = (dollars).roundTo(2),
                    coins = (dollars * it.tokenInfo.price.rate).roundTo(2),
                    symbol = it.tokenInfo.symbol,
                    hash = it.transactionHash
                )
                newArr.add(singleTrans)
            }
        }
        return TransactionListModel(
            transaction = newArr
        )
    }

    fun normalizeEthTrans(transactions: List<EtherTransModel>, rate: Double): TransactionListModel {
        val newArr: MutableList<TransactionModel> = ArrayList()

        transactions.forEach {
            val singleTrans = TransactionModel(
                from = it.from,
                to = it.to,
                date = convertDate(it.timestamp!!),
                timestamp = it.timestamp,
                dollars = (rate * it.value).roundTo(2),
                coins = it.value.roundTo(2),
                symbol = "ETH",
                hash = it.hash
            )
            newArr.add(singleTrans)
        }

        return TransactionListModel(
            transaction = newArr
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDate(timestamp: Number): String {
        val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
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
                // go to trans
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
            totalSum = totalSum.round(2),
            dailyMoney = dailyMoney.round(2),
            growth = dailyMoney > 0
        )
    }
}
