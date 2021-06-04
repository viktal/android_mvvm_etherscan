package main.src.etherscan.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils

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
                    inputMnemonic.visibility = View.INVISIBLE
                }
                TypeLogin.MNEMONIC.toString() -> {
                    inputAddress.visibility = View.INVISIBLE
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

                if (!MnemonicUtils.validateMnemonic(mnemonic)) {
                    Toast.makeText(
                        context,
                        "Enter correct BIP39 mnemonic!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val seed = MnemonicUtils.generateSeed(mnemonic, null)
                val masterKeypair = Bip32ECKeyPair.generateKeyPair(seed)
                val path = intArrayOf(44 or HARDENED_BIT, 60 or HARDENED_BIT, 0 or HARDENED_BIT, 0, 0)
                val x = Bip32ECKeyPair.deriveKeyPair(masterKeypair, path)
                val credentials: Credentials = Credentials.create(x)
                val walletAddress = credentials.address
                pressSubmit(walletAddress)
            } else {
                val address = binding.inputFieldAddress.text.toString()
                val regex = """0x[0-9a-fA-F]{40}""".toRegex()
                if (!regex.matches(address)) {
                    Toast.makeText(
                        context,
                        "Enter correct address!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.model.value!!.address = address
                pressSubmit(address)
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
