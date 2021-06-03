package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.ChartTimeDurations
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.models.ParsedHistoryGroupEth
import main.src.etherscan.data.repositories.EthplorerRepository

class ChartViewModel : ViewModel() {
    private var _model = MutableLiveData<HistoryGroupEth?>()
    val model: LiveData<HistoryGroupEth?>
        get() = _model

    private val repo = EthplorerRepository()

    fun fetchChartData() {
        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val history = repo.getHistoryGrouped()
            _model.postValue(history)
        }
    }
}

