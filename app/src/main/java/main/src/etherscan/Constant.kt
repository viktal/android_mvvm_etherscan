package main.src.etherscan

object BundleConstants {
    const val ADDRESS: String = "address"
    const val TYPELOGIN: String = "typeLogin"
    const val TYPETRANS: String = "typeTrans"
    const val TRANSADDRESS: String = "transAddress"

    const val MONEYCOUNT: String = "moneyCount"
    const val MONEYCOUNTDOLLAR: String = "moneyCountDollar"
    const val IMAGEPATH: String = "imagePath"

    const val RATEETH: String = "rate"
}

object ChartTimeDurations {
    const val Month1 = 31
    const val Month3 = 31*3
    const val Month6 = 31*6
    const val Year1 = 365
    const val Year3 = 365*3
    const val Max = -1
}

enum class TypeTrans {
    ETHER, TOKEN
}

enum class TypeLogin {
    ADDRESS, MNEMONIC
}

const val PAGE_START = 1
const val PAGE_SIZE = 10
