package com.template.wnfxtest.ui.items.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.template.models.GoodModel
import com.template.wnfxtest.R
import java.lang.Exception

class RVAdapter(private val goodsList: List<GoodModel>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var mListener : OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_good_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = goodsList[position]

        //item_loading
        Picasso.get()
            .load(currentItem.imageurl)
            .into(holder.img, object : Callback {
                override fun onSuccess() {
                    holder.cpv.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    holder.cpv.visibility = View.GONE
                    Toast.makeText(holder.itemView.context, "Load error", Toast.LENGTH_SHORT).show()
                }

            })
        holder.tvTitle.text = currentItem.name
        holder.tvInfo.text = currentItem.shortInfo
        holder.tvPrice.text = "${currentItem.price}₽"
        holder.tvRating.text = "${currentItem.rating}★"
        //не могу понять как подтянуть сюда строковый ресурс
        holder.addTocartBtn.setOnClickListener {  }
    }

    override fun getItemCount() =
        goodsList.size

    class ViewHolder(itemView: View, listener: OnItemClickListener) :RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.recycler_good_item_img)
        val tvTitle: TextView = itemView.findViewById(R.id.recycler_good_item_title)
        val tvPrice: TextView = itemView.findViewById(R.id.recycler_good_item_price)
        val tvInfo: TextView = itemView.findViewById(R.id.recycler_good_item_info)
        val tvRating: TextView = itemView.findViewById(R.id.recycler_good_item_rating)
        val cpv: CircularProgressView = itemView.findViewById(R.id.item_loading)
        val addTocartBtn: ImageButton = itemView.findViewById(R.id.recycler_good_item_add_cart)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(position = absoluteAdapterPosition)
            }
        }
    }
}