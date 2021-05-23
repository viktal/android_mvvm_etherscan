package main.src.etherscan.data.models

data class TransactionListModel(
    val transaction: MutableList<TransactionModel>,
    var prices: HistoryGroupEth?
)

data class TransactionModel(
    val from: String,
    val date: String,
    val to: String,
    val dollars: String,
    val coins: String,
    val symbol: String
)
