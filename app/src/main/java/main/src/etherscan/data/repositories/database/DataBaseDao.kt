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
    @Query("SELECT * from wallets WHERE walletAddress = :walletAddress")
    fun getWalletWithTokens(walletAddress: String): WalletWithTokens

    @Query("SELECT * from wallets WHERE walletAddress = :walletAddress")
    fun getWallet(walletAddress: String): WalletsDataBaseModel

    @Query("SELECT * from tokens WHERE tokenAddress = :tokenAddress")
    fun getToken(tokenAddress: String): TokensDataBaseModel

    @Query("DELETE FROM wallets")
    fun clear()

//    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC")
//    fun getAllTokensLists(): LiveData<List<TokensDataBaseModel>>
//
//    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC LIMIT 1")
//    suspend fun getNew(): TokensDataBaseModel?
}

