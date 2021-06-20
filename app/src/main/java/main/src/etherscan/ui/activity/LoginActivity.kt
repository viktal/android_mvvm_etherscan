package main.src.etherscan.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.adapters.LoginAdapter

class LoginActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FragmentStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.login_activity)

        intent.extras?.let {
            if (it.containsKey(BundleConstants.LOGIN_ERROR)) {
                Toast.makeText(
                    this, it.getString(BundleConstants.LOGIN_ERROR), Toast.LENGTH_LONG
                ).show()
            }
        }

        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        viewPager = findViewById(R.id.pager)
        adapter = LoginAdapter(this)
        viewPager.adapter = adapter

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.base_fragment) as NavHostFragment? ?: return

        navController = host.navController
        navController.navigate(R.id.authFragment)
    }
}
