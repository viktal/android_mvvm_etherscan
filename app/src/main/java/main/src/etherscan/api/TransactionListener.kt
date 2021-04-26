package main.src.etherscan.api

interface TransactionListener {
    fun pressTrans(address: String)
}
