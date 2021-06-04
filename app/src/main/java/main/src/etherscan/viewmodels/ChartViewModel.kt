package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.repositories.EthplorerRepository

class ChartViewModel : ViewModel() {
    private var _model = MutableLiveData<HistoryGroupEth?>()
    val model: LiveData<HistoryGroupEth?>
        get() = _model

    private val repo = EthplorerRepository()

    fun fetchChartData(handler: CoroutineExceptionHandler) {
        _model.value = null
        viewModelScope.launch(handler) {
            val history = repo.getHistoryGrouped()
            _model.postValue(history)
        }
    }
}
