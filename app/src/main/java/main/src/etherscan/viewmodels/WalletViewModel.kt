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
import main.src.etherscan.data.repositories.database.WalletWithTokens
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
//        storage?.insertWalletWithTokens(wallet = WalletWithTokens(WalletsDataBaseModel(str, 0,0), List(TokensDataBaseModel()))
//        val wallet2: WalletWithTokens? = storage?.getWallet("str")
//        storage?.clear()


        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {



            val value = str?.let { repo.getAddressInfo(it) }!!


            _model.postValue(
                value
            )
        }

    }
}
