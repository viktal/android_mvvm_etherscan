package main.src.etherscan.data.models

data class TransactionListModel(
    val transaction: MutableList<TransactionModel>
)

data class TransactionModel(
    var incomming: Boolean = false,
    val from: String,
    val date: String,
    val timestamp: Int,
    val to: String,
    val dollars: String,
    val coins: String,
    val symbol: String,
    val hash: String
)
