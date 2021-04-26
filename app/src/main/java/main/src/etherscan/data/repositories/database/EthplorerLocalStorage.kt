package main.src.etherscan.data.repositories

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import main.src.etherscan.data.models.MainPageTokenModel


@Entity(tableName = "tokens")
data class TokensListDataBaseModel(
    @PrimaryKey
    val walletAddress: String = "",

    @ColumnInfo(name = "all_tokens")
    val allTokens: MutableList<MainPageTokenModel>,

    @ColumnInfo(name = "total_sum")
    val totalSum: Long = 0,

    @ColumnInfo(name = "daily_money")
    val dailyMoney: Long = 0,

    @ColumnInfo(name = "save_timestamp")
    val startTimeMilli: Long = System.currentTimeMillis()
)

private const val SEPARATOR = ","

class DayOfWeekConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun allTokensToString(tokens: MutableList<MainPageTokenModel>?): String? =
            tokens?.joinToString(separator = SEPARATOR) { Gson().toJson(it) }

        @TypeConverter
        @JvmStatic
        fun stringToAllTokensList(tokensInStr: String?): MutableList<MainPageTokenModel>? =
            tokensInStr?.split(SEPARATOR)?.map { MainPageTokenModel(Gson().fromJson(it, MainPageTokenModel)) }?.toMutableList()
    }
}

