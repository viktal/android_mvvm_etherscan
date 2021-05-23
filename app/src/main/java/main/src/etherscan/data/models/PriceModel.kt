package main.src.etherscan.data.models

data class PriceModel(
    val rate: Double,
    val diff: Double,
    val diff7d: Double,
    val marketCapUsd: Double,
    val availableSupply: Double,
    val volume24h: Double,
    val ts: Int

)

data class PriceModelHistory(
    val ts: Int,
    val date: String,
    val hour: Int,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume: Double,
    val volumeConverted: Double,
    val cap: Double,
    val average: Double

)
