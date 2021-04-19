package main.src.etherscan.viewmodels

import androidx.lifecycle.ViewModel
import main.src.etherscan.api.AuthListener

class AuthViewModel : ViewModel() {
    var address: String? = null
    var mnemonicPhrase: String? = null
    var authListener: AuthListener? = null

    fun loginByAddress() {
    }

    fun loginByMnemonicPhrase() {
    }
}
