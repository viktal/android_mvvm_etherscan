package main.src.etherscan.data.models

data class PriceModel(
    val rate: Double,
    val currency: String? = null,
    val diff: Double,
    val diff7d: Double,
    val diff30d: Double? = 0.0,
    val marketCapUsd: Double,
    val availableSupply: Double,
    val volume24h: Double,
    val ts: Int,
    val volDiff1: Double? = 0.0,
    val volDiff7: Double? = 0.0,
    val volDiff30: Double? = 0.0
)
