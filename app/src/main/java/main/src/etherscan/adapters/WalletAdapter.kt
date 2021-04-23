package main.src.etherscan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.adapters.holders.WalletHolder
import main.src.etherscan.api.WalletListener
import main.src.etherscan.data.models.TokenInfoModel

class WalletAdapter(
    private var mData: MutableList<TokenInfoModel>,
    private val listener: WalletListener
) : RecyclerView.Adapter<WalletHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_tokens, parent, false)
        val holder = WalletHolder(view)
        // holder.setOnNumberClickListener(onNumberClickListener)
        return holder
    }

    override fun onBindViewHolder(holder: WalletHolder, position: Int) {

    }

    override fun getItemCount(): Int {
       return mData.size
    }
}