package main.src.etherscan.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import main.src.etherscan.R
import main.src.etherscan.api.WalletListener
import main.src.etherscan.ui.fragments.TransactionFragment
import main.src.etherscan.ui.fragments.WaitFragment

class MainActivity : AppCompatActivity(), WalletListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
