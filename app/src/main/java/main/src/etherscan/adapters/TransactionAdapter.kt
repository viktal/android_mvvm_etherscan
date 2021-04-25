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
import main.src.etherscan.data.models.ListEtherTransModel

import main.src.etherscan.data.models.TokenBalanceModel
import main.src.etherscan.data.models.TokensListModel

class TransactionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // var mTokenTitle: TextView = itemView.findViewById(R.id.token_title)
    // var mTokenMoneyCount: TextView = itemView.findViewById(R.id.money_count)
    // var mTokenDescription: TextView = itemView.findViewById(R.id.description)
    // var mTokenMoneyCountDollar: TextView = itemView.findViewById(R.id.money_count_dollar)
    // var mTokenImage: ImageView = itemView.findViewById(R.id.image)
    //
    // var mTokenItem: LinearLayout = itemView.findViewById(R.id.token_item)
}

class TransactionAdapter(
    private var mData: List<EtherTransModel>?,
    private val listener: TransactionListener
) : RecyclerView.Adapter<TransactionHolder>() {

    private val imageAddress = "https://ethplorer.io"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.single_transaction, parent, false)
        return TransactionHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {

        // val model = mData
        // holder.mTokenTitle.text = model!!.tokens[position].name
        // holder.mTokenDescription.text = model.tokens[position].rate + "(" + model.tokens[position].dif + "%)"
        // holder.mTokenMoneyCount.text =  model.tokens[position].balance + " " + model.tokens[position].symbol
        // holder.mTokenMoneyCountDollar.text ='$' + model.tokens[position].price
        // Picasso.get().load(imageAddress + model.tokens[position].logo).into(holder.mTokenImage)
        // holder.mTokenItem.setOnClickListener{
        //     listener.pressTrans(model.tokens[position].address)
        // }
    }

    override fun getItemCount(): Int {
        return 1
        // return mData!!.tokens.size
    }
}
