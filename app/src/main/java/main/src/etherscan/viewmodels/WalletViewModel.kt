package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.repositories.EthplorerRepository
import main.src.etherscan.data.repositories.database.TokensDataBaseModel
import main.src.etherscan.data.repositories.database.WalletsDataBaseModel
import main.src.etherscan.ui.activity.MainActivity


class WalletViewModel() : ViewModel() {

    private var _model = MutableLiveData<TokensListModel>()
    val model: LiveData<TokensListModel?>
        get() = _model

    private val repo = EthplorerRepository()


    fun clickOnSubmitBtn(str: String) {
        val storage = MainActivity.getDatabase()?.tokensDatabaseDao


        val value = str

//        val tokensList = repo.getAddressInfo(str).tokens
//
        storage?.insertWallet(wallet = WalletsDataBaseModel(walletAddress = str))
//        val wallet2: WalletWithTokens? = storage?.getWallet("str")
//        storage?.clear()

        val oldWalletWithTokens = storage?.getWalletWithTokens(walletAddress = str)
        val oldWallet = storage?.getWallet(walletAddress = str)

        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {



            val value = str?.let { repo.getAddressInfo(it) }!!

//            storage?.insertWallet(wallet = WalletsDataBaseModel(walletAddress = str, dailyMoney = value.dailyMoney.toLong(), totalSum = value.totalSum.toLong()))
//            value.tokens.forEach {
//                storage?.insertToken(TokensDataBaseModel(it.address, it.symbol, it.logo, it.rate, it.price, it.balance, it.name, it.dif))
//            }

            _model.postValue(
                value
            )
        }

    }
}
