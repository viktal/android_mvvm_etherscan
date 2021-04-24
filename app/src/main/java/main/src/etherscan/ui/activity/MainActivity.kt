package main.src.etherscan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import main.src.etherscan.R
import main.src.etherscan.data.repositories.EthplorerRepository
import main.src.etherscan.ui.fragments.WaitFragment
import main.src.etherscan.ui.fragments.WalletFragment
import main.src.etherscan.viewmodels.WalletViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val waitFragment = WaitFragment()
        if (supportFragmentManager.findFragmentById(R.id.base_fragment) == null) {
            supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.base_fragment, waitFragment)
                .commit()
        }
    }
}
