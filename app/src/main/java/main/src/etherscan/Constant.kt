package main.src.etherscan

object BundleConstants {
    var ADDRESS: String = "address"
    var TYPELOGIN: String = "typeLogin"
    var TYPETRANS: String = "typeTrans"
    var TRANSADDRESS: String = "transAddress"
}

enum class TypeTrans {
    ETHER, TOKEN
}

enum class TypeLogin {
    ADDRESS, MNEMONIC
}
