package xyz.geryakbarf.android.elibunikom.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.geryakbarf.android.elibunikom.R


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view : View = inflater.inflate(R.layout.fragment_profile,container,false)

        return view
    }
}