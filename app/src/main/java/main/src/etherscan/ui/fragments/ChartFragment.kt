package main.src.etherscan.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.button.MaterialButtonToggleGroup
import java.text.SimpleDateFormat
import kotlin.math.round
import kotlinx.coroutines.CoroutineExceptionHandler
import main.src.etherscan.ChartTimeDurations
import main.src.etherscan.R
import main.src.etherscan.data.models.HistoryGroupEth
import main.src.etherscan.data.models.ParsedHistoryGroupEth
import main.src.etherscan.databinding.ChartLayoutBinding
import main.src.etherscan.viewmodels.ChartViewModel

class ChartFragment : Fragment() {
    private lateinit var binding: ChartLayoutBinding
    private lateinit var viewModel: ChartViewModel
    private lateinit var mProgressBar: ProgressBar
    private lateinit var lineChart: LineChart
    private lateinit var toggleButton: MaterialButtonToggleGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.chart_layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        mProgressBar = binding.root.findViewById(R.id.chart_progress_bar)

        viewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        viewModel.fetchChartData(CoroutineExceptionHandler { _, exception ->
            Toast.makeText(activity, exception.message, Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.walletFragment)
        })

        val layout: LinearLayout = binding.root.findViewById(R.id.chart_layout)
        lineChart = binding.root.findViewById(R.id.lineChart)
        lineChart.axisRight.isEnabled = false
        lineChart.description.text = ""
        lineChart.setTouchEnabled(true)
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.legend.isEnabled = false

        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                createViewByModel(model, layout)
            }
        })

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun createViewByModel(model: HistoryGroupEth, layout: LinearLayout) {
        binding.chartViewModel = viewModel
        mProgressBar.visibility = View.GONE

        val groupEth = viewModel.model.value!!
        var chartData = parseHistoryGroupEth(groupEth, ChartTimeDurations.Year1)
        setLineChartData(chartData.xLabel, chartData.yArray)
        layout.visibility = View.VISIBLE
        lineChart.animateX(1800, Easing.EaseInExpo)

        val tokensCap = binding.root.findViewById<TextView>(R.id.tokens_cap)

        val cap = round(groupEth.totals.cap / 1000000)
        tokensCap.text = "Token capitalization\n$$cap billion"
        val tokensTotal = binding.root.findViewById<TextView>(R.id.total_tokens)
        tokensTotal.text = "Total tokens: ${groupEth.totals.tokensWithPrice}"

        toggleButton = binding.root.findViewById(R.id.toggleButton)
        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                chartData = when (checkedId) {
                    R.id.m1 -> parseHistoryGroupEth(model, ChartTimeDurations.Month1)
                    R.id.m3 -> parseHistoryGroupEth(model, ChartTimeDurations.Month3)
                    R.id.m6 -> parseHistoryGroupEth(model, ChartTimeDurations.Month6)
                    R.id.y1 -> parseHistoryGroupEth(model, ChartTimeDurations.Year1)
                    R.id.y3 -> parseHistoryGroupEth(model, ChartTimeDurations.Year3)
                    R.id.max -> parseHistoryGroupEth(model, ChartTimeDurations.Max)
                    else -> parseHistoryGroupEth(model, ChartTimeDurations.Year1)
                }

                setLineChartData(chartData.xLabel, chartData.yArray)
                lineChart.notifyDataSetChanged()
                lineChart.invalidate()
            }
        }
    }

    private fun parseHistoryGroupEth(historyGroupEth: HistoryGroupEth, duration: Int): ParsedHistoryGroupEth {
        val yArray = ArrayList<Entry>()
        val xLabel = ArrayList<String>()

        var c = 0f
        val last = historyGroupEth.prices.size - 1
        val d: Int
        if (duration == ChartTimeDurations.Max)
            d = last
        else d = duration
        for (i in (last - d)..last) {
            yArray.add(Entry(c, ((historyGroupEth.prices[i].open + historyGroupEth.prices[i].close) / 2).toFloat()))
            xLabel.add(historyGroupEth.prices[i].date)
            c += 1f
        }

        return ParsedHistoryGroupEth(yArray, xLabel, historyGroupEth.totals)
    }

    private fun setLineChartData(xLabel: ArrayList<String>, yArray: ArrayList<Entry>) {
        val xaxis: XAxis = lineChart.xAxis
        xaxis.granularity = 5f

        xaxis.valueFormatter = object : ValueFormatter() {
            private val mFormat = SimpleDateFormat("dd-MMM-yy")
            private val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            override fun getFormattedValue(value: Float): String {
                if (value < 0 || value >= xLabel.size)
                    return ""
                return mFormat.format(inputFormat.parse(xLabel[value.toInt()])!!)
            }
        }

        val lineDataSet = LineDataSet(yArray, "ETH price")
        lineDataSet.color = resources.getColor(R.color.color_bg_blue)
        lineDataSet.setDrawCircles(false)
        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.moveViewToX(yArray.last().x)
    }
}
