package main.src.etherscan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import main.src.etherscan.R
import main.src.etherscan.data.repositories.EthplorerRepository

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_login)
    }
}
