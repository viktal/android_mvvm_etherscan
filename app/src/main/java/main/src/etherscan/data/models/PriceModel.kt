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
