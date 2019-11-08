package xyz.geryakbarf.android.elibunikom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.geryakbarf.android.elibunikom.R
import xyz.geryakbarf.android.elibunikom.models.HotBookModels

class LatestBookAdapter(val listBooks: ArrayList<HotBookModels>) :
    RecyclerView.Adapter<LatestBookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_latest_books, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBooks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id, title, category, img) = listBooks[position]
        holder.idLatest = id
        holder.titleLatest.text = title
        holder.categoryLatest.text = category
        Glide.with(holder.itemView.context).load(img).into(holder.imgLatest)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idLatest: String = ""
        var imgLatest: ImageView = itemView.findViewById(R.id.imgLatestBooks)
        var titleLatest: TextView = itemView.findViewById(R.id.titleLatestBooks)
        var categoryLatest: TextView = itemView.findViewById(R.id.categoryLatestBooks)
    }
}