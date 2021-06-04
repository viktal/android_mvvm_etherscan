package main.src.etherscan.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.databinding.LoadingTransactionBinding

class LoadingTransactionViewHolder(
    private val binding: LoadingTransactionBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadingTransactionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_transaction, parent, false)
            val binding = LoadingTransactionBinding.bind(view)
            return LoadingTransactionViewHolder(binding, retry)
        }
    }
}
