package main.src.etherscan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import main.src.etherscan.R
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.api.WalletListener
import main.src.etherscan.data.models.EtherTransModel

import main.src.etherscan.data.models.TokenBalanceModel
import main.src.etherscan.data.models.TokensListModel
import main.src.etherscan.data.models.*

class TransactionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mTransDate: TextView = itemView.findViewById(R.id.date)
    val mTransAddress: TextView = itemView.findViewById(R.id.address)
    val mTransDollars: TextView = itemView.findViewById(R.id.money_count_dollar)
    val mTransCoins: TextView = itemView.findViewById(R.id.money_count)
}

class TransactionAdapter(
        private var mData:
        TransactionListModel?,
        private val listener: TransactionListener
) : RecyclerView.Adapter<TransactionHolder>() {

    private val imageAddress = "https://ethplorer.io"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.single_transaction, parent, false)
        return TransactionHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {

        val model = mData

        val from = model!!.transaction[position].to
        val fromLen = from.length

        holder.mTransDate.text = model.transaction[position].date
        holder.mTransAddress.text = "to: " + from.subSequence(0,5).toString() + ".." + from.subSequence(fromLen-5, fromLen-1)
        holder.mTransDollars.text = "$" + model.transaction[position].dollars
        holder.mTransCoins.text = model.transaction[position].coins + model.transaction[position].symbol
    }

    override fun getItemCount(): Int {
        return mData!!.transaction.size
    }
}
