package main.src.etherscan.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import main.src.etherscan.R
import main.src.etherscan.databinding.ChartLayoutBinding
import main.src.etherscan.viewmodels.ChartViewModel
import java.text.SimpleDateFormat
import kotlin.math.round

class ChartFragment : Fragment() {
    private lateinit var binding: ChartLayoutBinding
    private lateinit var viewModel: ChartViewModel
    private lateinit var mProgressBar: ProgressBar
    private lateinit var lineChart: LineChart

    @SuppressLint("SetTextI18n")
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
        viewModel.fetchChartData()



        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                binding.chartViewModel = viewModel
                mProgressBar.visibility = View.GONE

                val layout: LinearLayout = binding.root.findViewById(R.id.chart_layout)
                lineChart = binding.root.findViewById(R.id.lineChart)
                setLineChartData(model.xLabel, model.yArray)
                layout.visibility = View.VISIBLE

                val tokensCap = binding.root.findViewById<TextView>(R.id.tokens_cap)
                val model = viewModel.model.value!!
                val cap = round(model.totals.cap/1000000)
                tokensCap.text = "Token capitalization\n$$cap billion"
                val tokensTotal = binding.root.findViewById<TextView>(R.id.total_tokens)
                tokensTotal.text = """Total tokens: ${model.totals.tokensWithPrice}"""
            }
        })

        return binding.root
    }

    private fun setLineChartData(xLabel: ArrayList<String>, yArray: ArrayList<Entry>) {
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(false)
        lineChart.axisRight.isEnabled=false
        val xaxis:XAxis = lineChart.xAxis
        xaxis.granularity=1f
        xaxis.valueFormatter = object : ValueFormatter() {
            val pattern = "dd MMM yy"
            private val mFormat = SimpleDateFormat(pattern)
            private val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            override fun getFormattedValue(value: Float): String {
                return mFormat.format(inputFormat.parse(xLabel[value.toInt()]))
            }
        }

        val lineDataSet = LineDataSet(yArray, "ETH price")
        lineDataSet.color = R.color.color_bg_blue
        lineDataSet.setDrawCircles(false)
        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.setVisibleXRangeMaximum(365f)
        lineChart.moveViewToX(yArray.last().x)
        lineChart.description.text = ""
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.animateX(1800, Easing.EaseInExpo)
    }
}


