package main.src.etherscan.data.repositories.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.gson.Gson
import main.src.etherscan.data.models.MainPageTokenModel
import main.src.etherscan.data.repositories.TokensListDataBaseModel


/**
 * Defines methods for using the TokensListDataBaseModel class with Room.
 */
@Dao
interface TokensDatabaseDao {


    @Insert
    suspend fun insert(tokensList: TokensListDataBaseModel)



    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param tokensList new value to write
     */
    @Update
    suspend  fun update(tokensList: TokensListDataBaseModel)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from tokens WHERE walletAddress = :key")
    suspend fun get(key: Long): TokensListDataBaseModel?
    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM tokens")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC")
    fun getAllTokensLists(): LiveData<List<TokensListDataBaseModel>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM tokens ORDER BY save_timestamp DESC LIMIT 1")
    suspend fun getTonight(): TokensListDataBaseModel?
}