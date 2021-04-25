package main.src.etherscan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import main.src.etherscan.R
import main.src.etherscan.api.WalletListener
import main.src.etherscan.data.models.UserModel
import main.src.etherscan.viewmodels.AuthViewModel
import main.src.etherscan.viewmodels.WalletViewModel


class MainActivity : AppCompatActivity(), WalletListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extras = intent.extras
        var address:String = ""

        if (extras != null) {
            address = extras.getString("address").toString()
        }


        val viewModelWallet = ViewModelProvider(this).get(WalletViewModel::class.java)
        viewModelWallet.ClickOnSubmitBtn(address)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.base_fragment) as NavHostFragment? ?: return

        navController = host.navController
        navController.navigate(R.id.waitFragment)
    }

    override fun pressToken(address: String) {
        // val bundle = Bundle()
        // bundle.putString("address", address)
        navController.navigate(R.id.transactionFragment2)
    }
}
