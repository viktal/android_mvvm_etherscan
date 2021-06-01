package main.src.etherscan.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import main.src.etherscan.R
import main.src.etherscan.adapters.LoginAdapter
import ru.semper_viventem.backdrop.BackdropBehavior

class LoginActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FragmentStateAdapter
    private lateinit var foregroundContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        //
        //
        // fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
        //     if (this !is CoordinatorLayout.LayoutParams) throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")
        //
        //     (layoutParams as CoordinatorLayout.LayoutParams).behavior as? T
        //         ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
        // }
        //
        // foregroundContainer = findViewById(R.id.foregroundContainer)
        // val backdropBehavior: BackdropBehavior = foregroundContainer.findBehavior() // find behavior
        //
        // with(backdropBehavior) {
        //
        //     // Attach your back layout to behavior.
        //     // BackDropBehavior will find the toolbar itself.
        //     attachBackLayout(R.id.backLayout)
        //
        //     // Set navigation icons for toolbar
        //     // setClosedIcon(R.drawable.middle_left_triangle)
        //     // setOpenedIcon(R.drawable.ic_close)
        //
        //     // Add listener
        //     addOnDropListener(object : BackdropBehavior.OnDropListener {
        //         override fun onDrop(dropState: BackdropBehavior.DropState, fromUser: Boolean) {
        //             // TODO: handle listener
        //         }
        //     })
        // }
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
