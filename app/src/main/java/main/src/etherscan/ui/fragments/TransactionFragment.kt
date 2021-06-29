package main.src.etherscan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
import main.src.etherscan.adapters.LoadingTransactionAdapter
import main.src.etherscan.adapters.TransactionAdapter
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.databinding.TransactionsBinding
import main.src.etherscan.viewmodels.TransactionViewModel

class TransactionFragment : Fragment() {
    private lateinit var binding: TransactionsBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter
    private var fetchTransactionsJob: Job? = null

    private fun fetchTransactions(
        address: String,
        typeTrans: TypeTrans,
        transAddress: String,
        rate: Double,
        timestamp: Int
    ) {
        fetchTransactionsJob?.cancel()
        fetchTransactionsJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.fetchTransactions(address, typeTrans, transAddress, rate, timestamp).collect {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.requireArguments()

        val address = bundle.getString(BundleConstants.ADDRESS, "")
        val typeTrans = TypeTrans.valueOf(bundle.getString(BundleConstants.TYPETRANS, ""))
        val transAddress = bundle.getString(BundleConstants.TRANSADDRESS, "")
        val rate = bundle.getString(BundleConstants.RATEETH, "").toDouble()
        val timestamp = (System.currentTimeMillis() / 1000).toInt()
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        binding = TransactionsBinding.inflate(layoutInflater)
        binding.transViewModel = viewModel
        binding.retryButton.setOnClickListener { adapter.retry() }

        adapter = TransactionAdapter(activity as TransactionListener, address)
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.listTransactions.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            // binding.retryText.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    activity,
                    "\uD83D\uDE28 Wooops ${it.error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        fetchTransactions(address, typeTrans, transAddress, rate, timestamp)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        val bundle = this.requireArguments()
        val moneyCount = binding.mainInfoDollars
        moneyCount.text = bundle.getString(BundleConstants.MONEYCOUNT, "")

        val moneyCountDollar = binding.mainInfoCash
        moneyCountDollar.text = bundle.getString(BundleConstants.MONEYCOUNTDOLLAR, "")

        val image = binding.image
        val typeTrans = TypeTrans.valueOf(bundle.getString(BundleConstants.TYPETRANS, ""))
        if (typeTrans == TypeTrans.ETHER) {
            image.setImageResource(R.drawable.ethereum)
        } else {
            Picasso.get().load(bundle.getString(BundleConstants.IMAGEPATH, "")).into(image)
        }

        val recyclerView: RecyclerView = binding.listTransactions
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = LoadingTransactionAdapter { adapter.retry() }
        )
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        return binding.root
    }
}
