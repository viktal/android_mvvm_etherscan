package main.src.etherscan.ui.fragments
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import main.src.etherscan.R
import main.src.etherscan.adapters.WalletAdapter
import main.src.etherscan.ui.activity.MainActivity

class WalletFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.main_screen, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.list_tokens)

        val grid = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = grid

        val adapter = WalletAdapter()

        recyclerView.adapter = adapter
        val button = view.findViewById<Button>(R.id.button)

        return view
    }
}