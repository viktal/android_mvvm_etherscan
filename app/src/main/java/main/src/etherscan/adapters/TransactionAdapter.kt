package main.src.etherscan.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import main.src.etherscan.R
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.data.models.TransactionListModel

class TransactionHolder(itemView: View) : BaseViewHolder(itemView) {
    val mTransDate: TextView = itemView.findViewById(R.id.date)
    val mTransAddress: TextView = itemView.findViewById(R.id.address)
    val mTransDollars: TextView = itemView.findViewById(R.id.money_count_dollar)
    val mTransCoins: TextView = itemView.findViewById(R.id.money_count)
    override var mTokenItem: LinearLayout = itemView.findViewById(R.id.transaction_item)
    var mTokenArrow: ImageView = itemView.findViewById(R.id.arrow_image)

    init {
        ButterKnife.bind(this, itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBind(model: TransactionListModel, position: Int, address: String) {
        val to = model.transaction[position].to
        val toLen = to.length
        val from = model.transaction[position].from
        val fromLen = from.length

        mTransDate.text = model.transaction[position].date

        if (from == address) {
            mTransAddress.text = "to: " + to.subSequence(0, 5).toString() + ".." + to.subSequence(
                toLen - 5,
                toLen - 1
            )
            mTransDollars.text = "-$" + model.transaction[position].dollars
            mTransCoins.text = "-" + model.transaction[position].coins + model.transaction[position].symbol
        } else {
            mTransAddress.text = "from: " + from.subSequence(0, 5).toString() + ".." + from.subSequence(
                fromLen - 5,
                fromLen - 1
            )
            model.transaction[position].incomming = true
            mTokenArrow.setBackgroundResource(R.drawable.baseline_south_west_24)
            mTransDollars.text = "$" + model.transaction[position].dollars
            mTransCoins.text = model.transaction[position].coins + model.transaction[position].symbol
        }
    }

    override fun clear() {
    }
}

class ProgressHolder(itemView: View) : BaseViewHolder(itemView) {
    init {
        ButterKnife.bind(this, itemView)
    }

    override fun clear() {
    }
}

class TransactionAdapter(
    private var mData: TransactionListModel?,
    private val listener: TransactionListener,
    val address: String
) : RecyclerView.Adapter<BaseViewHolder>() {
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private val isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> TransactionHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.single_transaction,
                    parent,
                    false
                )
            )
            else -> ProgressHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        val model = mData
        //
        // val to = model!!.transaction[position].to
        // val toLen = to.length
        // val from = model.transaction[position].from
        // val fromLen = from.length
        //
        // holder.mTransDate.text = model.transaction[position].date
        //
        // if (from == address) {
        //     holder.mTransAddress.text = "to: " + to.subSequence(0, 5).toString() + ".." + to.subSequence(
        //         toLen - 5,
        //         toLen - 1
        //     )
        //     holder.mTransDollars.text = "-$" + model.transaction[position].dollars
        //     holder.mTransCoins.text = "-" + model.transaction[position].coins + model.transaction[position].symbol
        //
        // } else {
        //     holder.mTransAddress.text = "from: " + from.subSequence(0, 5).toString() + ".." + from.subSequence(
        //         fromLen - 5,
        //         fromLen - 1
        //     )
        //     model.transaction[position].incomming = true
        //     holder.mTokenArrow.setBackgroundResource(R.drawable.baseline_south_west_24)
        //     holder.mTransDollars.text = "$" + model.transaction[position].dollars
        //     holder.mTransCoins.text = model.transaction[position].coins + model.transaction[position].symbol
        // }

        holder.onBind(model!!, position, address)
        holder.mTokenItem.setOnClickListener {
            listener.pressTrans(model.transaction[position].hash,
                (model.transaction[position].coins + model.transaction[position].symbol),
                model.transaction[position].dollars)
        }
    }

    override fun getItemCount(): Int {
        return mData!!.transaction.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == itemCount - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    fun clear() {
    }
}
