package main.src.etherscan.api

interface TransactionListener {
    fun pressTrans(hash: String, moneyCount: String, moneyCountDollar: String)
}
