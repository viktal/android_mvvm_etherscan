package main.src.etherscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import main.src.etherscan.R
import main.src.etherscan.api.WalletListener
import main.src.etherscan.data.models.TokenBalanceModel

class LoginHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mTokenTitle: TextView = itemView.findViewById(R.id.token_title)
    var mTokenMoneyCount: TextView = itemView.findViewById(R.id.money_count)
    var mTokenDescription: TextView = itemView.findViewById(R.id.description)
    var mTokenMoneyCountDollar: TextView = itemView.findViewById(R.id.money_count_dollar)
    var mTokenImage: ImageView = itemView.findViewById(R.id.image)

    var mSubmitButton: LinearLayout = itemView.findViewById(R.id.submit_button)
}

class LoginAdapter(
    private var mData: MutableList<TokenBalanceModel>,
    private val listener: WalletListener
) : RecyclerView.Adapter<WalletHolder>() {

    private val imageAddress = "https://ethplorer.io"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.token, parent, false)
        return WalletHolder(view)
    }

    override fun onBindViewHolder(holder: WalletHolder, position: Int) {
        val model = mData[position]
        holder.mTokenTitle.text = model.tokenInfo.name
        holder.mTokenDescription.text = model.tokenInfo.price?.rate.toString()
        Picasso.get().load(imageAddress + model.tokenInfo.image).into(holder.mTokenImage)

        // holder.mTokenItem.setOnClickListener{
        //     listener.pressToken(model.tokenInfo.address)
        // }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
