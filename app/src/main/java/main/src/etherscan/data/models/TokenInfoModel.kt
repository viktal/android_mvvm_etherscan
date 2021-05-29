package main.src.etherscan.data.models

import android.util.Log
import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlin.math.log

data class TokenInfoModel(
    val address: String,
    val totalSupply: String,
    val name: String,
    val symbol: String,
    val decimals: String,
    val price: PriceModel?,
    val owner: String,
    val countOps: Int? = null,
    val transfersCount: Int? = null,
    val holdersCount: Int,
    val issuancesCount: Int,
    val lastUpdated: Int,
    val image: String = ""
)

class PriceModelOrFalseConverter : Converter {
    override fun canConvert(cls: Class<*>): Boolean =
        cls == PriceModel::class.java

    override fun fromJson(jv: JsonValue): PriceModel? {
        if (jv.boolean == null) {
            assert(jv.obj != null)
            return Klaxon().parseFromJsonObject<PriceModel>(jv.obj!!)
        } else {
            return null
        }
    }

    override fun toJson(value: Any): String {
        TODO("Not yet implemented")
    }
}
