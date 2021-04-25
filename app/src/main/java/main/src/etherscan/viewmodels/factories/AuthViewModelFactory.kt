package main.src.etherscan.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import main.src.etherscan.data.models.UserModel
import main.src.etherscan.data.repositories.UserRepository
import main.src.etherscan.viewmodels.AuthViewModel
import main.src.etherscan.viewmodels.WalletViewModel

//@Suppress("UNCHECKED_CAST")
//class WalletViewModelFactory(
//    private val model: UserModel
//) : ViewModelProvider.NewInstanceFactory() {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return WalletViewModel(model) as T
//    }
//}