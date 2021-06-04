package main.src.etherscan.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import main.src.etherscan.BundleConstants
import main.src.etherscan.R
import main.src.etherscan.TypeTrans
import main.src.etherscan.api.TransactionListener
import main.src.etherscan.api.WalletListener
import main.src.etherscan.viewmodels.WalletViewModel

class MainActivity : AppCompatActivity(), WalletListener, TransactionListener, Toolbar.OnMenuItemClickListener {
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<View>(R.id.toolbar_main) as Toolbar
        toolbar.inflateMenu(R.menu.top_app_bar)
        toolbar.setOnMenuItemClickListener(this)

        val extras = intent.extras
        var address = ""

        if (extras != null) {
            address = extras.getString(BundleConstants.ADDRESS).toString()
        }

        val viewModelWallet = ViewModelProvider(this).get(WalletViewModel::class.java)
        viewModelWallet.fetchAddressData(address, CoroutineExceptionHandler { _, exception ->
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(BundleConstants.LOGIN_ERROR, exception.message)
            startActivity(intent)
        })

        val bundle = Bundle()
        bundle.putString(BundleConstants.ADDRESS, address)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.base_fragment) as NavHostFragment? ?: return

        navController = host.navController
        navController.navigate(R.id.waitFragment, bundle)
    }

    override fun pressToken(
        address: String,
        typeTrans: TypeTrans,
        transAddress: String,
        moneyCount: String,
        moneyCountDollar: String,
        imagePath: String,
        rate: String
    ) {
        val bundle = Bundle()
        bundle.putString(BundleConstants.ADDRESS, address)
        bundle.putString(BundleConstants.TYPETRANS, typeTrans.toString())
        bundle.putString(BundleConstants.TRANSADDRESS, transAddress)

        bundle.putString(BundleConstants.MONEYCOUNT, moneyCount)
        bundle.putString(BundleConstants.MONEYCOUNTDOLLAR, moneyCountDollar)
        bundle.putString(BundleConstants.IMAGEPATH, imagePath)

        bundle.putString(BundleConstants.RATEETH, rate)

        navController.navigate(R.id.transactionFragment2, bundle)
    }

    override fun pressTrans(hash: String, moneyCount: String, moneyCountDollar: String) {
        val bundle = Bundle()
        bundle.putString(BundleConstants.ADDRESS, hash)
        bundle.putString(BundleConstants.MONEYCOUNT, moneyCount)
        bundle.putString(BundleConstants.MONEYCOUNTDOLLAR, moneyCountDollar)
        navController.navigate(R.id.transDetailsFragment, bundle)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.chart -> {
                navController.navigate(R.id.chartFragment, null)
                return true
            }
            R.id.logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return false
    }
}
