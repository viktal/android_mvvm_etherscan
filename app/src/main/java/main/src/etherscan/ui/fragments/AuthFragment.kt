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
import main.src.etherscan.TypeLogin
import main.src.etherscan.api.AuthListener
import main.src.etherscan.databinding.AccountLoginBinding
import main.src.etherscan.ui.activity.MainActivity
import main.src.etherscan.viewmodels.AuthViewModel
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.wallet.DeterministicKeyChain
import org.bitcoinj.wallet.DeterministicSeed
import org.web3j.crypto.Keys
import org.web3j.crypto.Sign

class AuthFragment : Fragment(), AuthListener {
    private lateinit var binding: AccountLoginBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var typeLogin: String
    private lateinit var inputAddress: View
    private lateinit var inputMnemonic: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.account_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        inputAddress = binding.root.findViewById<View>(R.id.input_address)
        inputMnemonic = binding.root.findViewById<View>(R.id.input_mnemonic)

        val arguments = arguments
        if (arguments != null) {
            typeLogin = arguments.getString(BundleConstants.TYPELOGIN).toString()
            when (typeLogin) {
                TypeLogin.ADDRESS.toString() -> {
                    inputAddress.visibility = View.VISIBLE
                    inputMnemonic.visibility = View.GONE
                }
                TypeLogin.MNEMONIC.toString() -> {
                    inputAddress.visibility = View.GONE
                    inputMnemonic.visibility = View.VISIBLE
                }
            }
        }

        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding.authViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.submit_button)
        button?.setOnClickListener {
            if (typeLogin == TypeLogin.MNEMONIC.toString()) {
                val mnemonic = binding.inputFieldMnemonic.text.toString()
                val seed = DeterministicSeed(mnemonic, null, "", 1409478661L)
                val chain = DeterministicKeyChain.builder().seed(seed).build()
                val addrKey = chain.getKeyByPath(HDUtils.parsePath("M/44H/60H/0H/0/0"), true)
                val walletAddress = Keys.getAddress(Sign.publicKeyFromPrivate(addrKey.privKey))
                pressSubmit("0x$walletAddress")
            } else {
                viewModel.model.value?.address?.let { it1 -> pressSubmit(it1) }
            }
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
