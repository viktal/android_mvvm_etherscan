package main.src.etherscan.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.api.AuthListener
import main.src.etherscan.databinding.AccountLoginBinding
import main.src.etherscan.ui.activity.MainActivity
import main.src.etherscan.viewmodels.AuthViewModel

class AuthFragment : Fragment(), AuthListener {
    private lateinit var binding: AccountLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.account_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding.authViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.submit_button)
        button?.setOnClickListener {
            viewModel.model.value?.address?.let { it1 -> pressSubmit(it1) }
        }
    }

    override fun pressSubmit(address: String) {
        // val walViewModel = ViewModelProvider(requireActivity()).get(WalletViewModel::class.java)
        // walViewModel.clickOnSubmitBtn(address)
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(BundleConstants.ADDRESS, address)
        startActivity(intent)
    }
}
