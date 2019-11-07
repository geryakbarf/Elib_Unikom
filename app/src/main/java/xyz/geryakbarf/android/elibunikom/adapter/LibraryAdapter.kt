package xyz.geryakbarf.android.elibunikom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.geryakbarf.android.elibunikom.R
import xyz.geryakbarf.android.elibunikom.models.LibraryModels

class LibraryAdapter(private val listLibary: ArrayList<LibraryModels>) :
    RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_library, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listLibary.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id, img, title) = listLibary[position]
        holder.idLibrary = id
        Glide.with(holder.itemView.context).load(img).into(holder.imgLibrary)
        holder.titleLibrary.text = title
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgLibrary: ImageView = itemView.findViewById(R.id.imgLibrary)
        var titleLibrary: TextView = itemView.findViewById(R.id.txtLibrary)
        var idLibrary: Int =0
    }
}