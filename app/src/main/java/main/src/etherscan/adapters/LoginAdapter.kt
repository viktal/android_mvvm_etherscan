package main.src.etherscan.adapters

import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.solver.state.State
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import main.src.etherscan.BundleConstants
import main.src.etherscan.TypeLogin
import main.src.etherscan.ui.fragments.AuthFragment

class LoginAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val authFragment = AuthFragment()
        val bundle = Bundle()
        when (position) {
            0 -> bundle.putString(BundleConstants.TYPELOGIN, TypeLogin.ADDRESS.toString())
            1 -> bundle.putString(BundleConstants.TYPELOGIN, TypeLogin.MNEMONIC.toString())
        }
        authFragment.arguments = bundle
        return authFragment
    }
}

