package main.src.etherscan.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.TypeTrans
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.models.ParsedHistoryGroupEth
import main.src.etherscan.data.repositories.EthplorerRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class ChartViewModel : ViewModel() {
    private var _model = MutableLiveData<ParsedHistoryGroupEth?>()
    val model: LiveData<ParsedHistoryGroupEth?>
        get() = _model

    private val repo = EthplorerRepository()


    fun fetchChartData() {
        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val history = repo.getHistoryGrouped()
            val parsed = parseHistoryGroupEth(history)
            _model.postValue(parsed)
        }
    }
}

fun parseHistoryGroupEth(historyGroupEth: HistoryGroupEth) : ParsedHistoryGroupEth {
    val yArray= ArrayList<Entry>()
    val xLabel  = ArrayList<String>()

    var c = 0f
    for (i in historyGroupEth.prices){
        if (i.date.startsWith("2021") || i.date.startsWith("2020")) {
            yArray.add(Entry(c, ((i.open + i.close)/2).toFloat()))
            xLabel.add(i.date)
            c+=1f
        }
    }

    return ParsedHistoryGroupEth(yArray, xLabel, historyGroupEth.totals)
}