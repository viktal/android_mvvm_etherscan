package main.src.etherscan.data.models

import com.beust.klaxon.Json
import com.github.mikephil.charting.data.Entry

data class HistoryGroupEth (
    @Json(ignored = true)
    var prices: MutableList<PriceModelHistory>,
    var totals: Totals
)

data class ParsedHistoryGroupEth (
    val yArray: ArrayList<Entry>,
    val xLabel: ArrayList<String>,
    var totals: Totals
)

data class Totals (
    val tokens: Int,
    val tokensWithPrice: Int,
    val cap: Double,
    val capPrevious: Double,
    // val volume24: Double,
    val volumePrevious: Double,
    val ts: Int
)

// val pathMatcher = object : PathMatcher {
//     override fun onMatch(path: String, value: Any) {
//         TODO("Not yet implemented")
//     }
//
//     override fun pathMatches(path: String) = Pattern.matches(".*prices.*", path)
// }
