package main.src.etherscan.data.repositories.database

import androidx.room.*


@Dao
interface TokensDatabaseDao {
    @Insert
    suspend fun insert(wallet: WalletsDataBaseModel)

    @Update
    suspend  fun update(wallet: WalletsDataBaseModel)

    @Transaction
    @Query("SELECT * from wallets WHERE walletAddress = :key")
    suspend fun getWallet(key: String): WalletWithTokens?

    @Query("SELECT * from tokens WHERE tokenAddress = :key")
    suspend fun getToken(key: String): TokensDataBaseModel?

    @Query("DELETE FROM wallets")
    suspend fun clear()

//    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC")
//    fun getAllTokensLists(): LiveData<List<TokensDataBaseModel>>
//
//    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC LIMIT 1")
//    suspend fun getNew(): TokensDataBaseModel?
}

