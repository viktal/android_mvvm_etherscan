package main.src.etherscan.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.repositories.EthplorerRepository

class WalletViewModel(private val repository: EthplorerRepository) : ViewModel() {


}
