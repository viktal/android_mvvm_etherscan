package main.src.etherscan.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
import main.src.etherscan.api.WalletListener
import main.src.etherscan.data.Constants.imageAddress
import main.src.etherscan.data.models.TokensListModel
import kotlin.coroutines.coroutineContext

class WalletHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mTokenTitle: TextView = itemView.findViewById(R.id.token_title)
    var mTokenMoneyCount: TextView = itemView.findViewById(R.id.money_count)
    var mTokenDescription: TextView = itemView.findViewById(R.id.description)
    var mTokenDescriptionPercent: TextView = itemView.findViewById(R.id.description_percent)
    var mTokenMoneyCountDollar: TextView = itemView.findViewById(R.id.money_count_dollar)
    var mTokenImage: ImageView = itemView.findViewById(R.id.image)
    var mTokenItem: LinearLayout = itemView.findViewById(R.id.token_item)
}

class WalletAdapter(
    private var mData: TokensListModel?,
    private val listener: WalletListener
) : RecyclerView.Adapter<WalletHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.token, parent, false)
        return WalletHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WalletHolder, position: Int) {

        val model = mData
//        val profit = model!!.dailyMoney.toDouble()*100/model.totalSum.toDouble()

        holder.mTokenTitle.text = model!!.tokens[position].name
        holder.mTokenDescription.text = "$" + model.tokens[position].rate
        holder.mTokenDescriptionPercent.text = "(" + model.tokens[position].dif + "%)"
        if (model.tokens[position].dif.toDouble() < 0) {
            holder.mTokenDescriptionPercent.setTextColor(Color.RED)
        } else {
            holder.mTokenDescriptionPercent.setTextColor(getColor(holder.mTokenItem.context,
                R.color.color_green))
        }

        val moneyCount = model.tokens[position].balance + " " + model.tokens[position].symbol
        holder.mTokenMoneyCount.text = moneyCount

        val moneyCountDollar = '$' + model.tokens[position].price
        holder.mTokenMoneyCountDollar.text = moneyCountDollar
//        holder.mDailyProfit.text = model.dailyMoney.toString() + "(" + profit + "%)"

        var imagePath = ""
        if (position == 0) {
            holder.mTokenImage.setImageResource(R.drawable.ethereum)
        } else {
            imagePath = imageAddress + model.tokens[position].logo
            Picasso.get().load(imagePath).into(holder.mTokenImage)
        }

        holder.mTokenItem.setOnClickListener {
            var typeTrans = TypeTrans.TOKEN
            if (position == 0) {
                typeTrans = TypeTrans.ETHER
                listener.pressToken(model.tokens[0].address, typeTrans, model.tokens[0].address,
                    moneyCount, moneyCountDollar, "", model.tokens[0].rate)
            } else {
                listener.pressToken(model.tokens[0].address, typeTrans, model.tokens[position].address,
                    moneyCount, moneyCountDollar, imagePath)
            }
        }
    }

    override fun getItemCount(): Int {
        return mData!!.tokens.size
    }

    fun clear() {
        mData!!.tokens.clear()
        mData = null
        notifyDataSetChanged()
    }

    fun addAll(newData: TokensListModel) {
        mData = newData
        mData!!.tokens.addAll(newData.tokens)
        notifyDataSetChanged()
    }
}
