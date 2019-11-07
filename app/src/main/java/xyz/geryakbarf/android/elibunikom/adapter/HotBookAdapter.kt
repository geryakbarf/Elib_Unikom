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

class HotBookAdapter (private val listBook : ArrayList<HotBookModels>) :
    RecyclerView.Adapter<HotBookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_hot_books,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(id,title,category,img)=listBook[position]
        holder.idBooks=id
        holder.titleBooks.text=title
        holder.categoryBooks.text=category
        Glide.with(holder.itemView.context).load(img).into(holder.imgBooks)
    }


    class ViewHolder (itemview : View) : RecyclerView.ViewHolder(itemview) {
        var idBooks : String = ""
        var imgBooks : ImageView = itemview.findViewById(R.id.imgHotBooks)
        var titleBooks : TextView = itemview.findViewById(R.id.titleHotBooks)
        var categoryBooks : TextView = itemview.findViewById(R.id.kategoriHotBooks)
    }
}