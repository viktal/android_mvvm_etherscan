package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.TypeTrans
import main.src.etherscan.data.models.TransactionListModel
import main.src.etherscan.data.repositories.EthplorerRepository

class TransactionViewModel : ViewModel() {
    private var _model = MutableLiveData<TransactionListModel?>()
    val model: LiveData<TransactionListModel?>
        get() = _model

    private val repo = EthplorerRepository()

    fun clickEther(address: String, typeTrans: TypeTrans, transAddress: String, rate: Double) {
        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val value = repo.getTrans(address, typeTrans, transAddress, rate)
            _model.postValue(value)
        }
    }
}
