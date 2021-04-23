package main.src.etherscan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import main.src.etherscan.R
import main.src.etherscan.data.repositories.EthplorerRepository
import main.src.etherscan.ui.fragments.WalletFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.base_fragment) == null) {
            val walletFragment = WalletFragment()
            supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.base_fragment, walletFragment)
                .commit()
        }
    }
}
