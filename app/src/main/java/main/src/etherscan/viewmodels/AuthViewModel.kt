package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.data.Constants
import main.src.etherscan.data.models.UserModel
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
}
