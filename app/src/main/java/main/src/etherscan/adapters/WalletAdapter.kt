package main.src.etherscan.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import main.src.etherscan.ETH_POS
import main.src.etherscan.IMAGE_ADDRESS
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
import main.src.etherscan.api.WalletListener
import main.src.etherscan.data.models.MainPageTokenModel
import main.src.etherscan.data.models.TokensListModel

class WalletHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mTokenTitle: TextView = itemView.findViewById(R.id.token_title)
    var mTokenMoneyCount: TextView = itemView.findViewById(R.id.money_count)
    var mTokenDescription: TextView = itemView.findViewById(R.id.description)
    var mTokenDescriptionPercent: TextView = itemView.findViewById(R.id.description_percent)
    var mTokenMoneyCountDollar: TextView = itemView.findViewById(R.id.money_count_dollar)
    var mTokenImage: ImageView = itemView.findViewById(R.id.image)
    var mTokenItem: ConstraintLayout = itemView.findViewById(R.id.token_item)
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
        holder.mTokenTitle.text = model!!.tokens[position].name
        holder.mTokenDescription.text = "$${model.tokens[position].rate}"
        holder.mTokenDescriptionPercent.text = "(${model.tokens[position].dif}%)"

        if (model.tokens[position].dif.toDouble() < 0) {
            holder.mTokenDescriptionPercent.setTextColor(Color.RED)
        } else {
            holder.mTokenDescriptionPercent.setTextColor(getColor(holder.mTokenItem.context,
                R.color.color_green))
        }

        val moneyCount = "${model.tokens[position].balance} ${model.tokens[position].symbol}"
        holder.mTokenMoneyCount.text = moneyCount

        val moneyCountDollar = "$${model.tokens[position].price}"
        holder.mTokenMoneyCountDollar.text = moneyCountDollar

        var imagePath: String? = null
        if (position == ETH_POS) {
            holder.mTokenImage.setImageResource(R.drawable.ethereum)
        } else {
            imagePath = IMAGE_ADDRESS + model.tokens[position].logo
            Picasso.get().load(imagePath).into(holder.mTokenImage)
        }

        holder.mTokenItem.setOnClickListener(
            makeListener(position, model.tokens, moneyCount, moneyCountDollar, imagePath)
        )
    }

    private fun makeListener(
        position: Int,
        tokens: MutableList<MainPageTokenModel>,
        moneyCount: String,
        moneyCountDollar: String,
        imagePath: String?
    ): View.OnClickListener {

        val walletAddress = tokens[ETH_POS].address

        return View.OnClickListener {
            if (position == ETH_POS) {
                listener.pressToken(walletAddress, TypeTrans.ETHER, tokens[ETH_POS].address,
                    moneyCount, moneyCountDollar, "", tokens[ETH_POS].rate)
            } else {
                listener.pressToken(walletAddress, TypeTrans.TOKEN, tokens[position].address,
                    moneyCount, moneyCountDollar, imagePath!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return mData!!.tokens.size
    }
}
