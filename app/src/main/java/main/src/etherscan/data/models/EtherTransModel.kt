package main.src.etherscan.data.models

data class EtherTransModel (
    val timestamp: Int,
    val from: String,
    val to: String,
    val hash: String,
    val value: Double,
    val input: String,
    val success: Boolean
)