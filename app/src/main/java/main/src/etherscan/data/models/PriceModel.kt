package main.src.etherscan.data.models

data class PriceModel(
    val rate: Double,
    val currency: String,
    val diff: Double,
    val diff7d: Double,
    val diff30d: Double,
    val marketCapUsd: Double,
    val availableSupply: Int,
    val volume24h: Double,
    val ts: Int
)
