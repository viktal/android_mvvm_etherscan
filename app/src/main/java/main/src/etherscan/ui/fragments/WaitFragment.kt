package main.src.etherscan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import main.src.etherscan.R
import main.src.etherscan.ui.activity.MainActivity
import main.src.etherscan.viewmodels.WalletViewModel

class WaitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(WalletViewModel::class.java)

        viewModel.model.observe(viewLifecycleOwner, Observer { model ->
            if (model != null) {
                val walletFragment = WalletFragment()

                // if (parentFragmentManager.findFragmentById(R.id.base_fragment) == null) {
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.base_fragment, walletFragment)
                    .commit()
                // }
            }
        })

        val view: View = inflater.inflate(R.layout.wait_fragment, container, false)

        return view
    }
}
