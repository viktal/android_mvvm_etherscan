package main.src.etherscan.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
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
import java.util.concurrent.TimeUnit

class ChartFragment : Fragment() {
    private lateinit var binding: ChartLayoutBinding
    private lateinit var viewModel: ChartViewModel
    private lateinit var mProgressBar: ProgressBar
    private lateinit var lineChart: LineChart

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

        val set1 = LineDataSet(yArray, "ETH price")
        set1 .color = Color.BLUE
        set1 .setDrawCircles(false)
        set1 .setDrawValues(false)
        val data = LineData(set1)
        lineChart.data = data
        lineChart.setVisibleXRangeMaximum(365f)
        lineChart.moveViewToX(yArray.last().x)
        lineChart.animateX(1800, Easing.EaseInExpo)





        //Part1
//         val entries = ArrayList<Entry>()
//
//
//
// //Part2
//         entries.add(Entry(1f, 10f))
//         entries.add(Entry(2f, 2f))
//         entries.add(Entry(3f, 7f))
//         entries.add(Entry(4f, 20f))
//         entries.add(Entry(5f, 16f))




//Part3
//         val vl = LineDataSet(entries, "My Type")
//
// //Part4
//         vl.setDrawValues(false)
//         vl.setDrawFilled(true)
//         vl.lineWidth = 3f
//         vl.fillColor = R.color.color_bg_blue
//         vl.fillAlpha = R.color.black
//
// //Part5
//         lineChart.xAxis.labelRotationAngle = 0f
//
// //Part6
//         lineChart.data = LineData(vl)
//
// // //Part7
//         lineChart.axisRight.isEnabled = false
//         lineChart.xAxis.axisMaximum = j +0.1f
//
// //Part8
//         lineChart.setTouchEnabled(true)
//         lineChart.setPinchZoom(true)
//
// //Part9
//         lineChart.description.text = "Days"
//         lineChart.setNoDataText("No forex yet!")
//
// //Part10
//         lineChart.animateX(1800, Easing.EaseInExpo)

//Part11
    }
}
