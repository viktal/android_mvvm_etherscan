package main.src.etherscan.data.models

data class ETHModel(
    val balance: Double = 0.0,
    val totalIn: Double = 0.0,
    val totalOut: Double = 0.0,
    val price: PriceModel
)
