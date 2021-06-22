package main.src.etherscan.data.models

import com.github.mikephil.charting.data.Entry
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryGroupEth(
    @Transient
    var countTxs: Array<Any>? = null,
    @Transient
    var cap: Array<Any>? = null,
    var prices: MutableList<PriceModelHistory>,
    var totals: Totals,
    var current: Current
)

data class ParsedHistoryGroupEth(
    val yArray: ArrayList<Entry>,
    val xLabel: ArrayList<String>,
    var totals: Totals
)

data class Totals(
    val tokens: Int,
    val tokensWithPrice: Int,
    val cap: Double,
    val capPrevious: Double,
    val volumePrevious: Double,
    val ts: Int
)

data class Current(
    val rate: Double,
    val diff: Double,
    val diff7d: Double,
    val ts: Int,
    val marketCapUsd: Double,
    val availableSupply: Double,
    val volume24h: Double,
    val diff30d: Double,
    val volDiff1: Double,
    val volDiff7: Double,
    val volDiff30: Double
)
