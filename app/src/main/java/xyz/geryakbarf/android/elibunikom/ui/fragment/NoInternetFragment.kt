package xyz.geryakbarf.android.elibunikom.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_no_internet.*
import xyz.geryakbarf.android.elibunikom.R

class NoInternetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_no_internet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRetry.setOnClickListener {
            val t: FragmentTransaction = this.fragmentManager!!.beginTransaction()
            val fragment = HomeFragment()
            t.replace(R.id.nav_host_fragment, fragment)
            t.commit()
        }
    }
}