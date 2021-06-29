package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.models.TokenDetailsModel
import main.src.etherscan.data.repositories.EthplorerRepository

class TransDetailsViewModel : ViewModel() {
    private var _model = MutableLiveData<TokenDetailsModel?>()
    val model: LiveData<TokenDetailsModel?>
        get() = _model

    private val repo = EthplorerRepository()

    fun pressTrans(address: String, moneyCount: String, moneyCountDollar: String, handler: CoroutineExceptionHandler) {
        _model.value = null
        val context = Dispatchers.IO.plus(handler)
        viewModelScope.launch(context) {
            val value = repo.getTransDetails(address)
            value.moneyCount = moneyCount
            value.moneyCountDollar = moneyCountDollar
            _model.postValue(value)
        }
    }
}
