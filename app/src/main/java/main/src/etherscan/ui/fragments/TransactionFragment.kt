package main.src.etherscan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
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

        val bundle = this.requireArguments()

        val address = bundle.getString(BundleConstants.ADDRESS, "")
        val typeTrans = bundle.getString(BundleConstants.TYPETRANS, "")
        val transAddress = bundle.getString(BundleConstants.TRANSADDRESS, "")

        binding = DataBindingUtil.inflate(inflater, R.layout.transactions, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        mProgressBar = binding.root.findViewById(R.id.trans_progress_bar)

        val moneyCount = binding.root.findViewById<TextView>(R.id.main_info_dollars)
        moneyCount.text = bundle.getString(BundleConstants.MONEYCOUNT, "")

        val moneyCountDollar = binding.root.findViewById<TextView>(R.id.main_info_cash)
        moneyCountDollar.text = bundle.getString(BundleConstants.MONEYCOUNTDOLLAR, "")

        val image = binding.root.findViewById<ImageView>(R.id.image)
        if (typeTrans == TypeTrans.ETHER.toString()) {
            image.setImageResource(R.drawable.ethereum)
        } else {
            Picasso.get().load(bundle.getString(BundleConstants.IMAGEPATH, "")).into(image)
        }

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        viewModel.clickEther(address, TypeTrans.valueOf(typeTrans), transAddress)

        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                binding.transViewModel = viewModel
                mProgressBar.visibility = View.GONE

                val layout: CoordinatorLayout = binding.root.findViewById(R.id.trans_coord_layout)
                val recyclerView: RecyclerView = binding.root.findViewById(R.id.list_transactions)
                recyclerView.adapter = TransactionAdapter(
                    viewModel.model.value,
                    activity as TransactionListener,
                    address
                )
                recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
                layout.visibility = View.VISIBLE
            }
        })

        return binding.root
    }
}
