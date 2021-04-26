package main.src.etherscan.data.repositories.database

import androidx.room.*


@Entity(tableName = "wallets")
data class WalletsDataBaseModel(
    @PrimaryKey
    val walletAddress: String = "",

    @ColumnInfo(name = "total_sum")
    val totalSum: Long = 0,

    @ColumnInfo(name = "daily_money")
    val dailyMoney: Long = 0,

    @ColumnInfo(name = "save_timestamp")
    val startTimeMilli: Long = System.currentTimeMillis()
)

@Entity(tableName = "tokens")
data class TokensDataBaseModel(
    @PrimaryKey
    val tokenAddress: String = "",

    @ColumnInfo(name = "symbol")
    val symbol: String = "",

    @ColumnInfo(name = "logo")
    val logo: String = "",

    @ColumnInfo(name = "rate")
    val rate: String = "",

    @ColumnInfo(name = "price")
    val price: String = "",

    @ColumnInfo(name = "balance")
    val balance: String = "",

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "dif")
    val dif: String = "",

    @ColumnInfo(name = "save_timestamp")
    val startTimeMilli: Long = System.currentTimeMillis()
)


data class WalletWithTokens(
    @Embedded val wallet: WalletsDataBaseModel,
    @Relation(
        parentColumn = "walletAddress",
        entityColumn = "tokenAddress"
    )
    val tokensList: List<TokensDataBaseModel>
)
