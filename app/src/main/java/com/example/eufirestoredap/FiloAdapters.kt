package com.example.eufirestoredap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FiloAdapters (
    private var filoList: MutableList<Filosofia>,
    private val context: Context,
    val onItemClick : (Int) -> Unit
): RecyclerView.Adapter<FiloAdapters.filoHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): filoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_filo, parent, false)
        return (filoHolder(view))
    }

    override fun onBindViewHolder(holder: filoHolder, position: Int) {
        holder.setName(filoList[position].nombre)
        Glide
            .with(context)
            .load(filoList[position].foto)
            .centerInside()
            .into(holder.getImageView())

        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return filoList.size
    }


    class filoHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var v: View

        init {
            this.v = v
        }

        fun setName(name: String) {
            val texName: TextView = v.findViewById(R.id.filo_item_text)
            texName.text = name
        }

        fun getCardLayout(): CardView {
            return v.findViewById(R.id.card_package_item)
        }

        fun getImageView(): ImageView {
            return v.findViewById(R.id.img_item)
        }
    }
}