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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.adapters.TransactionAdapter
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.databinding.TransactionsBinding
import main.src.etherscan.viewmodels.TransactionViewModel




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

        var address = ""
        var typeTrans = ""

        val bundle = this.arguments
        if (bundle != null) {
            address = bundle.getString(BundleConstants.ADDRESS, "")
            typeTrans = bundle.getString(BundleConstants.TYPETRANS, "")
        }

        val view = inflater
                .inflate(R.layout.transactions, container, false)

        mProgressBar = view.findViewById(R.id.trans_progress_bar)

        binding = DataBindingUtil.inflate(inflater, R.layout.transactions, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        binding.transViewModel = viewModel

        val recyclerView: RecyclerView = view.findViewById(R.id.list_transactions)
        recyclerView.adapter = TransactionAdapter(
            viewModel.model.value,
            activity as TransactionListener
        )



        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                binding.transViewModel = viewModel
                mProgressBar.visibility = View.GONE
                val layout: CoordinatorLayout = view.findViewById(R.id.trans_coord_layout)
                layout.visibility = View.VISIBLE

                val recyclerView: RecyclerView = view.findViewById(R.id.list_transactions)
                recyclerView.adapter = TransactionAdapter(
                        viewModel.model.value,
                        activity as TransactionListener
                )
                recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
            }
        })

        return view
    }
}