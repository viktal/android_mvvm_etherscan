package main.src.etherscan.data.repositories.database

import android.content.Context
import androidx.room.*

/**
 * A database that stores WalletsDataBaseModel information.
 * And a global method to get access to the database.
 */
@Database(
    entities = [WalletsDataBaseModel::class, TokensDataBaseModel::class],
    version = 1,
    exportSchema = false
)
abstract class TokensDataBase : RoomDatabase() {
    abstract val tokensDatabaseDao: TokensDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: TokensDataBase? = null

        fun getInstance(context: Context): TokensDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TokensDataBase::class.java,
                        "ether_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
