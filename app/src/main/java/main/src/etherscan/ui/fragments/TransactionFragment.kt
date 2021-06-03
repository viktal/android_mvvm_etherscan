package main.src.etherscan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import main.src.etherscan.BundleConstants
import main.src.etherscan.PAGE_SIZE
import main.src.etherscan.PAGE_START
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
import main.src.etherscan.adapters.TransactionAdapter
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.databinding.TransactionsBinding
import main.src.etherscan.viewmodels.TransactionViewModel

class TransactionFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: TransactionsBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var mProgressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: TransactionAdapter

    private var currentPage: Int = 1
    private var isLastPage = false
    private val totalPage = 10
    private var isLoading = false
    var itemCount = 0

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
        val rate = bundle.getString(BundleConstants.RATEETH, "")

        binding = DataBindingUtil.inflate(inflater, R.layout.transactions, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        mProgressBar = binding.root.findViewById(R.id.trans_progress_bar)
        swipeRefresh = binding.root.findViewById(R.id.swipeContainer)
        swipeRefresh.setOnRefreshListener(this)

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
        val tsLong = System.currentTimeMillis() / 1000

        viewModel.clickEther(address, TypeTrans.valueOf(typeTrans), transAddress, rate.toDouble(), tsLong.toInt())

        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                binding.transViewModel = viewModel
                mProgressBar.visibility = View.GONE

                val layout: LinearLayout = binding.root.findViewById(R.id.trans_coord_layout)
                val recyclerView: RecyclerView = binding.root.findViewById(R.id.list_transactions)
                recyclerView.setHasFixedSize(true)
                adapter = TransactionAdapter(
                    viewModel.model.value,
                    activity as TransactionListener,
                    address
                )
                recyclerView.adapter = adapter
                val layoutManager = LinearLayoutManager(binding.root.context)
                recyclerView.layoutManager = layoutManager
                layout.visibility = View.VISIBLE

                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val visibleItemCount: Int = layoutManager.childCount
                        val totalItemCount: Int = layoutManager.itemCount
                        val firstVisibleItemPosition: Int =
                            layoutManager.findFirstVisibleItemPosition()
                        if (!isLastPage && !isLoading) {
                            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                                val timestamp = viewModel.model.value!!.transaction.last().timestamp
                                loadMoreItems(address, typeTrans, transAddress, rate, timestamp)
                                isLoading = false
                            }
                        }
                    }
                })
            }
        })

        return binding.root
    }

    private fun loadMoreItems(
        address: String,
        typeTrans: String,
        transAddress: String,
        rate: String,
        timestamp: Int
    ) {
        isLoading = true
        currentPage++
        viewModel.clickEther(
            address,
            TypeTrans.valueOf(typeTrans),
            transAddress,
            rate.toDouble(),
            timestamp
        )
    }

    override fun onRefresh() {
        itemCount = 0
        currentPage = PAGE_START
        isLastPage = false
        adapter.clear()
    }
}
