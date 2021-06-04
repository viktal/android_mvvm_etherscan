package main.src.etherscan.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class LoadingTransactionAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingTransactionViewHolder>() {
    override fun onBindViewHolder(holder: LoadingTransactionViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingTransactionViewHolder {
        return LoadingTransactionViewHolder.create(parent, retry)
    }
}
