package main.src.etherscan.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import main.src.etherscan.data.Constants
import main.src.etherscan.data.models.UserModel

class AuthViewModel() : ViewModel() {

    var address = MutableLiveData<String>()
    var mnemonic = MutableLiveData<String>()

    private var _model = MutableLiveData<UserModel>()
    val model: LiveData<UserModel>
        get() = _model

    init {
        val value = UserModel(
            address = Constants.addressPlaceHolder,
            mnemonic = Constants.mnemonicPlaceHolder
        )
        _model.postValue(
            value
        )
    }
}
