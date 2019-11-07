package xyz.geryakbarf.android.elibunikom.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import xyz.geryakbarf.android.elibunikom.adapter.LibraryAdapter
import xyz.geryakbarf.android.elibunikom.models.HotBookModels
import xyz.geryakbarf.android.elibunikom.models.LibraryModels
import xyz.geryakbarf.android.elibunikom.models.data.LibraryData

class HomeFragment : Fragment() {

    private val list: ArrayList<LibraryModels> = arrayListOf()
    private var listHot: ArrayList<HotBookModels> = arrayListOf()
    private val URL = "http://android.geryakbarf.xyz/elib/getBooks.php"

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
        list.addAll(LibraryData.listLibrary)
        rvLibrary.setHasFixedSize(true)
        rvHotBooks.setHasFixedSize(true)
        showLibrary()
        showHotBooks()
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
                        else
                            judul = judul

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
                    placeHolderHotBooks.visibility=View.GONE
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
    }

    override fun onPause() {
        placeHolderHotBooks.stopShimmerAnimation()
        super.onPause()

    }
}