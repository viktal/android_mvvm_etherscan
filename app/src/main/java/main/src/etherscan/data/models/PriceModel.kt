package main.src.etherscan.data.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class PriceModel(
    val rate: Double,
    val diff: Double,
    val diff7d: Double,
    val marketCapUsd: Double,
    val availableSupply: Double,
    val volume24h: Double,
    val ts: Int
)

data class PriceModelHistory(
    val ts: Int,
    val date: String,
    val hour: Int,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume: Double,
    val volumeConverted: Double,
    val cap: Double,
    val average: Double
)

internal class PriceJsonAdapter : JsonAdapter<PriceModel>() {
    @FromJson
    override fun fromJson(reader: JsonReader): PriceModel? {
        if (reader.peek().name == "BEGIN_OBJECT") {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val adapter = moshi.adapter<PriceModel>(PriceModel::class.java)
            return adapter.fromJson(reader)
        } else {
            reader.nextBoolean()
            return null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: PriceModel?) {
        TODO("Not yet implemented")
    }
}
