package main.src.etherscan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.adapters.TransactionAdapter
import main.src.etherscan.adapters.WalletAdapter
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.api.WalletListener
import main.src.etherscan.databinding.MainScreenBinding
import main.src.etherscan.databinding.TransactionsBinding
import main.src.etherscan.viewmodels.TransactionViewModel
import main.src.etherscan.viewmodels.WalletViewModel

class TransactionFragment : Fragment() {
    private lateinit var binding: TransactionsBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var mProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater
            .inflate(R.layout.transactions, container, false)

        mProgressBar = view.findViewById(R.id.trans_progress_bar)

        binding = DataBindingUtil.inflate(inflater, R.layout.transactions, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        // binding.walletViewModel = viewModel

        val recyclerView: RecyclerView = view.findViewById(R.id.list_transactions)
        recyclerView.adapter = TransactionAdapter(viewModel.model.value, activity as TransactionListener)

        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                mProgressBar.visibility = View.GONE
                val layout : CoordinatorLayout = view.findViewById(R.id.trans_coord_layout)
                layout.visibility = View.VISIBLE
            }
        })


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}