package xyz.geryakbarf.android.elibunikom.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONException
import xyz.geryakbarf.android.elibunikom.R
import xyz.geryakbarf.android.elibunikom.adapter.HotBookAdapter
import xyz.geryakbarf.android.elibunikom.adapter.LatestBookAdapter
import xyz.geryakbarf.android.elibunikom.adapter.LibraryAdapter
import xyz.geryakbarf.android.elibunikom.models.HotBookModels
import xyz.geryakbarf.android.elibunikom.models.LibraryModels
import xyz.geryakbarf.android.elibunikom.models.data.LibraryData


class HomeFragment : Fragment() {

    private val list: ArrayList<LibraryModels> = arrayListOf()
    private var listHot: ArrayList<HotBookModels> = arrayListOf()
    private val listLatest: ArrayList<HotBookModels> = arrayListOf()
    private val URL = "http://android.geryakbarf.xyz/elib/getBooks.php"
    private val URL2 = "http://android.geryakbarf.xyz/elib/getLatest.php"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnection()

        list.addAll(LibraryData.listLibrary)
        rvLibrary.setHasFixedSize(true)
        rvHotBooks.setHasFixedSize(true)
        rvHLatestBooks.setHasFixedSize(true)
        showLibrary()
        showHotBooks()
        showLatestBooks()
    }

    private fun showLibrary() {
        rvLibrary.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val libraryAdapter = LibraryAdapter(list)
        rvLibrary.adapter = libraryAdapter
    }

    private fun showHotBooks() {
        val stringRequest = StringRequest(Request.Method.GET, URL,
            Response.Listener { response ->
                try {
                    //
                    val array = JSONArray(response)
                    //
                    for (i in 0 until array.length()) {
                        val ob = array.getJSONObject(i)
                        var judul = ob.getString("judul")
                        if (judul.length > 25)
                            judul = judul.substring(0, 26) + "..."

                        val listData = HotBookModels(
                            ob.getString("id"),
                            judul,
                            ob.getString("kategori"),
                            ob.getString("img")
                        )
                        listHot.add(listData)
                    }
                    rvHotBooks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    val hotAdapter = HotBookAdapter(listHot)
                    rvHotBooks.adapter = hotAdapter
                    placeHolderHotBooks.stopShimmerAnimation()
                    placeHolderHotBooks.visibility = View.GONE
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    private fun showLatestBooks() {
        val stringRequest = StringRequest(Request.Method.GET, URL2,
            Response.Listener { response ->
                try {
                    //
                    val array = JSONArray(response)
                    //
                    for (i in 0 until array.length()) {
                        val ob = array.getJSONObject(i)
                        var judul = ob.getString("judul")
                        if (judul.length > 44)
                            judul = judul.substring(0, 45) + "..."

                        val listData = HotBookModels(
                            ob.getString("id"),
                            judul,
                            ob.getString("kategori"),
                            ob.getString("img")
                        )
                        listLatest.add(listData)
                    }
                    rvHLatestBooks.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    val latestAdapter = LatestBookAdapter(listLatest)
                    rvHLatestBooks.adapter = latestAdapter
                    ViewCompat.setNestedScrollingEnabled(rvHLatestBooks, false)
                    placeHolderLatestBooks.stopShimmerAnimation()
                    placeHolderLatestBooks.visibility = View.GONE
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    override fun onResume() {
        super.onResume()
        placeHolderHotBooks.startShimmerAnimation()
        placeHolderLatestBooks.startShimmerAnimation()
    }

    override fun onPause() {
        placeHolderHotBooks.stopShimmerAnimation()
        placeHolderLatestBooks.stopShimmerAnimation()
        super.onPause()
    }

    private fun checkConnection() {
        val connect =
            activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED ||
            connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED
        )
        //do Nothing
        else {
            val t: FragmentTransaction = this.fragmentManager!!.beginTransaction()
            val fragment = NoInternetFragment()
            t.replace(R.id.nav_host_fragment, fragment)
            t.commit()
        }
    }
}