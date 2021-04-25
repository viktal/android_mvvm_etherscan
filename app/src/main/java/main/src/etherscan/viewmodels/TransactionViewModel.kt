package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.models.EtherTransModel
import main.src.etherscan.data.models.ListEtherTransModel
import main.src.etherscan.data.repositories.EthplorerRepository

class TransactionViewModel : ViewModel() {
    private var _model = MutableLiveData<List<EtherTransModel>?>()
    val model: LiveData<List<EtherTransModel>?>
        get() = _model

    private val repo = EthplorerRepository()

    fun clickEther(str: String) {
        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val value = str.let { repo.getEtherTrans(it) }
            _model.postValue(
                value
            )
        }
    }

}