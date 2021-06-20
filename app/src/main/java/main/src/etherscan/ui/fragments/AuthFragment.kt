package main.src.etherscan.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.TypeLogin
import main.src.etherscan.api.AuthListener
import main.src.etherscan.ui.activity.MainActivity
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils

class AuthFragment : Fragment(), AuthListener {
    private lateinit var typeLogin: String
    private lateinit var inputAddress: View
    private lateinit var inputMnemonic: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.account_login, container, false)

        inputAddress = view.findViewById<View>(R.id.input_address)
        inputMnemonic = view.findViewById<View>(R.id.input_mnemonic)

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.submit_button)
        button?.setOnClickListener {
            if (typeLogin == TypeLogin.MNEMONIC.toString()) {
                val mnemonic = view.findViewById<MaterialAutoCompleteTextView>(R.id.input_field_mnemonic).text.toString()
                if (!MnemonicUtils.validateMnemonic(mnemonic)) {
                    Toast.makeText(
                        context,
                        "Enter correct BIP39 mnemonic!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                pressSubmit(generateAddress(mnemonic))
            } else {
                val address = view.findViewById<MaterialAutoCompleteTextView>(R.id.input_field_address).text.toString()
                val regex = "0x[0-9a-fA-F]{40}".toRegex()
                if (!regex.matches(address)) {
                    Toast.makeText(
                        context,
                        "Enter correct address!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                pressSubmit(address)
            }
        }
    }

    private fun generateAddress(mnemonic: String): String {
        val seed = MnemonicUtils.generateSeed(mnemonic, null)
        val masterKeypair = Bip32ECKeyPair.generateKeyPair(seed)
        val path = intArrayOf(44 or HARDENED_BIT, 60 or HARDENED_BIT, 0 or HARDENED_BIT, 0, 0)
        val x = Bip32ECKeyPair.deriveKeyPair(masterKeypair, path)
        val credentials: Credentials = Credentials.create(x)
        return credentials.address
    }

    override fun pressSubmit(address: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(BundleConstants.ADDRESS, address)
        startActivity(intent)
    }
}
