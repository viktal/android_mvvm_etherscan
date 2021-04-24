package main.src.etherscan.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.adapters.WalletAdapter
import main.src.etherscan.api.WalletListener
import main.src.etherscan.databinding.MainScreenBinding
import main.src.etherscan.viewmodels.WalletViewModel

class WalletFragment : Fragment() {
    private lateinit var binding: MainScreenBinding
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.main_screen, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(requireActivity()).get(WalletViewModel::class.java)
        binding.walletViewModel = viewModel

        val recyclerView: RecyclerView = binding.root.findViewById(R.id.list_tokens)
        recyclerView.adapter = WalletAdapter(viewModel.model.value!!.tokens, context as WalletListener)
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        return binding.root
    }
}
