package main.src.etherscan

object BundleConstants {
    const val ADDRESS: String = "address"
    const val TYPELOGIN: String = "typeLogin"
    const val TYPETRANS: String = "typeTrans"
    const val TRANSADDRESS: String = "transAddress"

    const val MONEYCOUNT: String = "moneyCount"
    const val MONEYCOUNTDOLLAR: String = "moneyCountDollar"
    const val IMAGEPATH: String = "imagePath"
}

enum class TypeTrans {
    ETHER, TOKEN
}

enum class TypeLogin {
    ADDRESS, MNEMONIC
}
