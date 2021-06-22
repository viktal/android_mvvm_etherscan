package main.src.etherscan.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import main.src.etherscan.R
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.data.models.TransactionModel

class TransactionAdapter(
    private val listener: TransactionListener,
    val address: String
) : PagingDataAdapter<TransactionModel, TransactionViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_transaction,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        if (transaction != null) {
            holder.onBind(transaction, address)
            holder.mTokenItem.setOnClickListener {
                listener.pressTrans(
                    transaction.hash,
                    (transaction.coins + transaction.symbol),
                    transaction.dollars
                )
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<TransactionModel>() {
            override fun areItemsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean =
                areContentsTheSame(oldItem, newItem)

            override fun areContentsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean =
                oldItem == newItem
        }
    }
}
