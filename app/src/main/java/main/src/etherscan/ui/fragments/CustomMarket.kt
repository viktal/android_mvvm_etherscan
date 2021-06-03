package main.src.etherscan.ui.fragments

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.highlight.Highlight
import main.src.etherscan.R

class CustomMarkerView(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    private var tvContent: TextView = findViewById<TextView>(R.id.tv_content)

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    fun refreshContent(e: Map.Entry<*, *>, highlight: Highlight?) {
        tvContent.text = "Val:" + e.key // set the entry-value as the display text
    }

    fun getXOffset(xpos: Float): Int {
        // this will center the marker-view horizontally
        return -(width / 2)
    }

    fun getYOffset(ypos: Float): Int {
        // this will cause the marker-view to be above the selected value
        return -height
    }
}
