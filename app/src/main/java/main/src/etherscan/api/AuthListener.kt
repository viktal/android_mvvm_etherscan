package main.src.etherscan.api

interface AuthListener {

    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}
