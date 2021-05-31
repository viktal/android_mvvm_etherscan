package main.src.etherscan.data.models


class UserModel(var address: String, var mnemonic: String = "") {
    fun isAddressValid(): Boolean {
        return true
    }
    fun isMnemonicValid(): Boolean {
        return true
    }
}