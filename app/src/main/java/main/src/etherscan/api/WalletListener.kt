package main.src.etherscan.api

import main.src.etherscan.TypeTrans

interface WalletListener {
    fun pressToken(address: String, typeTrans: TypeTrans)
}
