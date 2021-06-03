package main.src.etherscan.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Date
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.databinding.TransDetailsFragmentBinding
import main.src.etherscan.viewmodels.TransDetailsViewModel

class TransDetailsFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: TransDetailsFragmentBinding
    private lateinit var viewModel: TransDetailsViewModel
    private lateinit var mProgressBar: ProgressBar

    private lateinit var copyHash: Button
    private lateinit var copyTo: Button
    private lateinit var copyFrom: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val bundle = this.requireArguments()
        val address = bundle.getString(BundleConstants.ADDRESS, "")
        val moneyCount = bundle.getString(BundleConstants.MONEYCOUNT, "")
        val moneyCountDollar = bundle.getString(BundleConstants.MONEYCOUNTDOLLAR, "")

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.trans_details_fragment,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        mProgressBar = binding.root.findViewById(R.id.trans_det_progress_bar)
        copyHash = binding.root.findViewById(R.id.copy_hash)
        copyTo = binding.root.findViewById(R.id.copy_to)
        copyFrom = binding.root.findViewById(R.id.copy_from)
        copyHash.setOnClickListener(this)
        copyTo.setOnClickListener(this)
        copyFrom.setOnClickListener(this)

        viewModel = ViewModelProvider(this).get(TransDetailsViewModel::class.java)
        viewModel.pressTrans(address, moneyCount, moneyCountDollar)

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

    override fun onClick(v: View?) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var text = ""
        when (v!!.id) {
            R.id.copy_hash -> text = binding.root.findViewById<TextView>(R.id.trans_hash).text as String
            R.id.copy_to -> text = binding.root.findViewById<TextView>(R.id.trans_to).text as String
            R.id.copy_from -> text = binding.root.findViewById<TextView>(R.id.trans_from).text as String
        }

        val clip = ClipData.newPlainText("Copied Text", text)
        Toast.makeText(requireContext(), "Text copied", Toast.LENGTH_SHORT).show()
        clipboard.setPrimaryClip(clip)
    }
}
