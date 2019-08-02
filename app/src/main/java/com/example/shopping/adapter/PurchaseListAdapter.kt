package com.example.shopping.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopping.R
import com.example.shopping.data.Purchase
import kotlinx.android.synthetic.main.item_purchase.view.*

class PurchaseListAdapter : RecyclerView.Adapter<PurchaseListAdapter.PurchaseHolder>() {

    private val list = mutableListOf<Purchase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchase, parent, false)
        return PurchaseHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PurchaseHolder, position: Int) {
        holder.bind(list[position])
    }

    class PurchaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(purchase: Purchase) {
            itemView.item_purchase_title.text = purchase.title
            Glide
                    .with(itemView.item_purchase_img)
                    .load(purchase.thumb)
                    .into(itemView.item_purchase_img)
        }
    }

    fun setData(list : List<Purchase>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(item : List<Purchase>) {
        list.addAll(item)
        notifyItemRangeInserted(itemCount, item.size)
    }

    fun deleteAll() {
        list.clear()
        notifyDataSetChanged()
    }
}