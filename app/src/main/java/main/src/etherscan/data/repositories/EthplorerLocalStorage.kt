package main.src.etherscan.data.repositories

import android.content.SharedPreferences
import com.google.gson.Gson
import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.models.ETHModel
import main.src.etherscan.data.models.PriceModel

class EthplorerLocalStorage {

    fun converter(model: AddressInfoModel) {
        val testModel: AddressInfoModel = model
        val gson = Gson()


        val obj: String = gson.toJson(model)

    }

}
