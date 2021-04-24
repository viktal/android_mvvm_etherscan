package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.models.AddressInfoModel
import main.src.etherscan.data.repositories.EthplorerRepository

class WalletViewModel : ViewModel() {
// livedata(для чертового адаптера)
//     адаптер на него подпишется
//     и обновится, когда придут данные в модель
//     через Трансормацию отдать список токенов

    private var _model = MutableLiveData<AddressInfoModel>()
    val model: LiveData<AddressInfoModel>
        get() = _model

    private val repo = EthplorerRepository()

    init {
        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val value = repo.getAddressInfo("0xf33ddb3eb90a88e1ebe3f0994a02ef2db4efe397?apiKey=freekey&showETHTotals=true")!!
            _model.postValue(
                value
            )
        }
    }
}
