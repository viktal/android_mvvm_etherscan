package main.src.etherscan.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.repositories.EthplorerRepository
import main.src.etherscan.data.repositories.UserRepository

class WalletViewModel() : ViewModel() {

    private var _model = MutableLiveData<TokensListModel>()
    val model: LiveData<TokensListModel?>
        get() = _model

    private val repo = EthplorerRepository()


    fun clickOnSubmitBtn(str: String) {


//        _model.value = null
//        viewModelScope.launch(Dispatchers.IO) {
//            val value = str?.let { repo.getAddressInfo(it) }!!
//            _model.postValue(
//                value
//            )
//        }

    }
}
