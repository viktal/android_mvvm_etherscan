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
import main.src.etherscan.TypeTrans
import main.src.etherscan.api.WalletListener
import main.src.etherscan.data.models.TokensListModel

class WalletHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mTokenTitle: TextView = itemView.findViewById(R.id.token_title)
    var mTokenMoneyCount: TextView = itemView.findViewById(R.id.money_count)
    var mTokenDescription: TextView = itemView.findViewById(R.id.description)
    var mTokenMoneyCountDollar: TextView = itemView.findViewById(R.id.money_count_dollar)
    var mTokenImage: ImageView = itemView.findViewById(R.id.image)
    var mTokenItem: LinearLayout = itemView.findViewById(R.id.token_item)
}

class WalletAdapter(
    private var mData: TokensListModel?,
    private val listener: WalletListener
    // private val imageAddress = ""
) : RecyclerView.Adapter<WalletHolder>() {

    private val imageAddress = "https://ethplorer.io"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.token, parent, false)
        return WalletHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WalletHolder, position: Int) {

        val model = mData
//        val profit = model!!.dailyMoney.toDouble()*100/model.totalSum.toDouble()

        holder.mTokenTitle.text = model!!.tokens[position].name
        holder.mTokenDescription.text = model.tokens[position].rate + "(" + model.tokens[position].dif + "%)"
        holder.mTokenMoneyCount.text =  model.tokens[position].balance + " " + model.tokens[position].symbol
        holder.mTokenMoneyCountDollar.text ='$' + model.tokens[position].price
//        holder.mDailyProfit.text = model.dailyMoney.toString() + "(" + profit + "%)"
        Picasso.get().load(imageAddress + model.tokens[position].logo).into(holder.mTokenImage)
        holder.mTokenItem.setOnClickListener{
            var typeTrans = TypeTrans.TOKEN
            if (position == 0)
                typeTrans = TypeTrans.ETHER
            listener.pressToken(model.tokens[position].address, typeTrans)
        }
    }

    override fun getItemCount(): Int {
        return mData!!.tokens.size
    }
}
