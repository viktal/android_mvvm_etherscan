package main.src.etherscan.adapters

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.data.models.TransactionListModel

abstract class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    var currentPosition = 0
        private set
    open lateinit var mTokenItem: LinearLayout

    protected abstract fun clear()
    open fun onBind(model: TransactionListModel, position: Int, address: String) {
        currentPosition = position
        clear()
    }
}