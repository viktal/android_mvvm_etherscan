package main.src.etherscan.data.repositories.database

import androidx.room.*


@Dao
interface TokensDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallet(wallet: WalletsDataBaseModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: TokensDataBaseModel)

    //fun insertWalletWithTokens(wallet: WalletWithTokens)

    @Update
    fun update(wallet: WalletsDataBaseModel)

    @Transaction
    @Query("SELECT * from wallets WHERE walletAddress = :key")
    fun getWallet(key: String): WalletWithTokens

    @Query("SELECT * from tokens WHERE tokenAddress = :key")
    fun getToken(key: String): TokensDataBaseModel

    @Query("DELETE FROM wallets")
    fun clear()

//    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC")
//    fun getAllTokensLists(): LiveData<List<TokensDataBaseModel>>
//
//    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC LIMIT 1")
//    suspend fun getNew(): TokensDataBaseModel?
}

