package main.src.etherscan.api

interface TransactionListener {
    fun pressTrans(hash: String)
}
