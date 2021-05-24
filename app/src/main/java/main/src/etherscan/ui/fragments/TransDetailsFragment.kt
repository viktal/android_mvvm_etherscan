package main.src.etherscan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.internal.s
import com.squareup.picasso.Picasso
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
import main.src.etherscan.adapters.TransactionAdapter
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.databinding.TransDetailsFragmentBinding
import main.src.etherscan.databinding.TransactionsBinding
import main.src.etherscan.viewmodels.TransDetailsViewModel
import main.src.etherscan.viewmodels.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Date

class TransDetailsFragment : Fragment() {
    private lateinit var binding: TransDetailsFragmentBinding
    private lateinit var viewModel: TransDetailsViewModel
    private lateinit var mProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val bundle = this.requireArguments()

        val address = bundle.getString(BundleConstants.ADDRESS, "")

        binding = DataBindingUtil.inflate(inflater, R.layout.trans_details_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        mProgressBar = binding.root.findViewById(R.id.trans_det_progress_bar)

        viewModel = ViewModelProvider(this).get(TransDetailsViewModel::class.java)
        viewModel.pressTrans(address)

        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                binding.transDetailsViewModel = viewModel
                mProgressBar.visibility = View.GONE

                val layout: LinearLayout = binding.root.findViewById(R.id.trans_details_linear)
                layout.visibility = View.VISIBLE

                val timestampLayout = binding.root.findViewById<TextView>(R.id.timestamp)
                val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
                val netDate = viewModel.model.value?.timestamp?.let { Date(it.toLong() * 1000) }
                timestampLayout.text = sdf.format(netDate)
            }
        })

        return binding.root
    }
}