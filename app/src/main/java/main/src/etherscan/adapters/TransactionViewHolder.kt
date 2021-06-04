package main.src.etherscan.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.data.models.TransactionModel

class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mTransDate: TextView = itemView.findViewById(R.id.date)
    private val mTransAddress: TextView = itemView.findViewById(R.id.address)
    private val mTransDollars: TextView = itemView.findViewById(R.id.money_count_dollar)
    private val mTransCoins: TextView = itemView.findViewById(R.id.money_count)
    var mTokenItem: LinearLayout = itemView.findViewById(R.id.transaction_item)
    private var mTokenArrow: ImageView = itemView.findViewById(R.id.arrow_image)

    @SuppressLint("SetTextI18n")
    fun onBind(transaction: TransactionModel, address: String) {
        val to = transaction.to
        val toLen = to.length
        val from = transaction.from
        val fromLen = from.length

        mTransDate.text = transaction.date

        if (from == address) {
            mTransAddress.text = "to: " + to.subSequence(0, 5).toString() + ".." + to.subSequence(
                toLen - 5,
                toLen - 1
            )
            mTransDollars.text = "-$" + transaction.dollars
            mTransCoins.text =
                "-" + transaction.coins + transaction.symbol

            mTransDollars.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_red))
            mTokenArrow.setBackgroundResource(R.drawable.baseline_north_east_24)
        } else {
            mTransAddress.text =
                "from: " + from.subSequence(0, 5).toString() + ".." + from.subSequence(
                    fromLen - 5,
                    fromLen - 1
                )
            transaction.incomming = true
            mTransDollars.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.color_green
                )
            )
            mTokenArrow.setBackgroundResource(R.drawable.baseline_south_west_24)
            mTransDollars.text = "$" + transaction.dollars
            mTransCoins.text = transaction.coins + transaction.symbol
        }
    }
}
