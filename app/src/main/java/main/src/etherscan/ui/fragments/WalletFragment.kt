package main.src.etherscan.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.src.etherscan.R
import main.src.etherscan.data.repositories.EthplorerRepository

class WalletFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.main_screen, container, false)

        val repo = EthplorerRepository()

        lifecycleScope.launch(Dispatchers.IO) {
            val model = repo.getAddressInfo("0xf3db5fa2c66b7af3eb0c0b782510816cbe4813b8?apiKey=freekey&showETHTotals=true")
            print(model)
        }




        // val recyclerView: RecyclerView = view.findViewById(R.id.list_tokens)
        //
        // val grid = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        // recyclerView.layoutManager = grid
        //
        // val adapter = WalletAdapter()
        //
        // recyclerView.adapter = adapter
        // val button = view.findViewById<Button>(R.id.button)

        return view
    }
}