package main.src.etherscan.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.models.MainPageTokenModel
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


    fun clickOnSubmitBtn(walletAddress: String) {
        val storage = MainActivity.getDatabase()
        var walletTokens: TokensListModel? = null
        _model.value = null

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("tag_wallet", "storage2" + storage.toString())
            //storage?.clear()
            walletTokens = walletAddress.let { repo.getAddressInfo(it) }
            val oldWalletWithTokens = storage?.getWalletWithTokens(walletAddress = walletAddress)
            if (oldWalletWithTokens == null) {
                storage?.insertWallet(wallet = WalletsDataBaseModel(walletAddress = walletAddress))
                walletTokens!!.tokens.forEach {
                    storage?.insertToken(
                        TokensDataBaseModel(
                            it.address,
                            walletAddress,
                            it.symbol,
                            it.logo,
                            it.rate,
                            it.price,
                            it.balance,
                            it.name,
                            it.dif
                        )
                    )
                }
            } else {
                val tokensInDatabase: MutableList<MainPageTokenModel> = mutableListOf()
                oldWalletWithTokens.tokensList.forEach {
                    tokensInDatabase.add(
                        MainPageTokenModel(
                            it.tokenAddress,
                            it.symbol,
                            it.logo,
                            it.rate,
                            it.price,
                            it.balance,
                            it.name,
                            it.dif
                        )
                    )
                }

                walletTokens = TokensListModel(
                    totalSum = oldWalletWithTokens.wallet.totalSum,
                    dailyMoney = oldWalletWithTokens.wallet.dailyMoney,
                    tokens = tokensInDatabase
                )
            }
            Log.d("tag_wallet", oldWalletWithTokens.toString())
            _model.postValue(
                walletTokens
            )
        }

    }
}
