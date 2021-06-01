package main.src.etherscan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.data.models.TransactionListModel

class TransactionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mTransDate: TextView = itemView.findViewById(R.id.date)
    val mTransAddress: TextView = itemView.findViewById(R.id.address)
    val mTransDollars: TextView = itemView.findViewById(R.id.money_count_dollar)
    val mTransCoins: TextView = itemView.findViewById(R.id.money_count)
    var mTokenItem: LinearLayout = itemView.findViewById(R.id.transaction_item)
    var mTokenArrow: ImageView = itemView.findViewById(R.id.arrow_image)
}

class TransactionAdapter(
    private var mData: TransactionListModel?,
    private val listener: TransactionListener,
    val address: String
) : RecyclerView.Adapter<TransactionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.single_transaction, parent, false)
        return TransactionHolder(view)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {

        val model = mData

        val to = model!!.transaction[position].to
        val toLen = to.length
        val from = model.transaction[position].from
        val fromLen = from.length

        holder.mTransDate.text = model.transaction[position].date

        if (from == address) {
            holder.mTransAddress.text = "to: " + to.subSequence(0, 5).toString() + ".." + to.subSequence(toLen - 5, toLen - 1)
            holder.mTransDollars.text = "-$" + model.transaction[position].dollars
            holder.mTransCoins.text = "-" + model.transaction[position].coins + model.transaction[position].symbol

        } else {
            holder.mTransAddress.text = "from: " + from.subSequence(0, 5).toString() + ".." + from.subSequence(fromLen - 5, fromLen - 1)
            model.transaction[position].incomming = true
            holder.mTokenArrow.setBackgroundResource(R.drawable.baseline_south_west_24)
            holder.mTransDollars.text = "$" + model.transaction[position].dollars
            holder.mTransCoins.text = model.transaction[position].coins + model.transaction[position].symbol
        }



        holder.mTokenItem.setOnClickListener {
            listener.pressTrans(hash = model.transaction[position].hash)
        }
    }

    override fun getItemCount(): Int {
        return mData!!.transaction.size
    }
}
