package main.src.etherscan.viewmodels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.Constants
import main.src.etherscan.data.models.UserModel
import main.src.etherscan.data.repositories.EthplorerRepository
import main.src.etherscan.data.repositories.UserRepository

class AuthViewModel() : ViewModel() {

    private var _model = MutableLiveData<UserModel>()
    val model: LiveData<UserModel>
        get() = _model

    private val repo = UserRepository()

    init {
        _model.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val value = UserModel(
                address = Constants.addressPlaceHolder,
                mnemonic = Constants.mnemonicPlaceHolder
            )
            _model.postValue(
                value
            )
        }
    }


    fun getAddress(): String? {
        return model.value?.address
    }

    fun setAddress(value: String) {

        if (model.value?.address != value) {
            model.value?.address = value

        }
    }
}

