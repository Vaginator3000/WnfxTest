package com.template.wnfxtest.ui.cart.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.template.models.GoodModelCart
import com.template.wnfxtest.R
import com.template.wnfxtest.ui.cart.CartViewModel
import com.template.wnfxtest.ui.items.recycler.ItemsRVAdapter
import java.lang.Exception

class CartRVAdapter(val goodsInCart: MutableList<GoodModelCart>) : RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onClick(position: Int)
    }
    private lateinit var incAmountClickListener : OnClickListener
    private lateinit var redAmountClickListener : OnClickListener

    fun setReduceAmountBtnClickListener(listener: OnClickListener) {
        redAmountClickListener = listener
    }
    fun setIncreaseAmountBtnClickListener(listener: OnClickListener) {
        incAmountClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_fragment_recycler_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = goodsInCart[position].good

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
        holder.tvAmount.text = goodsInCart[position].amount.toString()

        holder.incAmountBtn.setOnClickListener {
            val newItem = GoodModelCart(
                good = currentItem,
                amount = goodsInCart[position].amount + 1
            )
            goodsInCart.removeAt(position)
            goodsInCart.add(position, newItem)
            notifyDataSetChanged()
        }

        holder.redAmountBtn.setOnClickListener {
            if (goodsInCart[position].amount != 1) {
                val newItem = GoodModelCart(
                    good = currentItem,
                    amount = goodsInCart[position].amount - 1
                )
                goodsInCart.removeAt(position)
                goodsInCart.add(position, newItem)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() =
        goodsInCart.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.cart_fragment_item_img)
        val tvTitle: TextView = itemView.findViewById(R.id.cart_fragment_item_title)
        val tvPrice: TextView = itemView.findViewById(R.id.cart_fragment_item_price)
        val tvInfo: TextView = itemView.findViewById(R.id.cart_fragment_item_info)
        val tvRating: TextView = itemView.findViewById(R.id.cart_fragment_item_rating)
        val cpv: CircularProgressView = itemView.findViewById(R.id.cart_fragment_item_loading)
        val tvAmount: TextView = itemView.findViewById(R.id.cart_fragment_amount)
        val incAmountBtn: Button = itemView.findViewById(R.id.cart_fragment_amount_increase)
        val redAmountBtn: Button = itemView.findViewById(R.id.cart_fragment_amount_reduce)

       /* init {
            incAmountBtn.setOnClickListener { incAmountClickListener.onClick(absoluteAdapterPosition) }
            redAmountBtn.setOnClickListener { redAmountClickListener.onClick(absoluteAdapterPosition) }
        }*/
    }
}