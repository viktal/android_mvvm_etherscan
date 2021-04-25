package main.src.etherscan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.api.WalletListener
import main.src.etherscan.viewmodels.TransactionViewModel
import main.src.etherscan.viewmodels.WalletViewModel


class MainActivity : AppCompatActivity(), WalletListener, TransactionListener {
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
        viewModelWallet.clickOnSubmitBtn(address)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.base_fragment) as NavHostFragment? ?: return




        navController = host.navController
        navController.navigate(R.id.waitFragment)
    }

    override fun pressToken(address: String, typeTrans: TypeTrans) {
        val bundle = Bundle()
        bundle.putString(BundleConstants.ADDRESS, address)
        bundle.putString(BundleConstants.TYPETRANS, typeTrans.toString())

        val viewModelTrans = ViewModelProvider(this).get(TransactionViewModel::class.java)
        viewModelTrans.clickEther(address, typeTrans)
        navController.navigate(R.id.transactionFragment2)
    }

    override fun pressTrans(address: String) {
        TODO("Not yet implemented")
    }
}
