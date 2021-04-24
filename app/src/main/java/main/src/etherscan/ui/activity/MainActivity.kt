package main.src.etherscan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import main.src.etherscan.R
import main.src.etherscan.ui.fragments.WaitFragment

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
