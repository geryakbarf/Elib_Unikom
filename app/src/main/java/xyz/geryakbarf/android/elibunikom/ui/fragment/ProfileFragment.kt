package xyz.geryakbarf.android.elibunikom.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import xyz.geryakbarf.android.elibunikom.R
import xyz.geryakbarf.android.elibunikom.utils.Session


class ProfileFragment : Fragment() {

    lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view : View = inflater.inflate(R.layout.fragment_profile,container,false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(view.context)

        if (session.getValueBoolean("isLogin") == false) {
            val t: FragmentTransaction = this.fragmentManager!!.beginTransaction()
            val fragment = NotSigninFragment()
            t.replace(R.id.nav_host_fragment, fragment)
            t.commit()
        }

    }
}