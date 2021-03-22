package com.sn.socketbitfinex_kotlinflow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sn.socketbitfinex_kotlinflow.R
import com.sn.socketbitfinex_kotlinflow.databinding.OrderBookBidItemBinding
import com.sn.socketbitfinex_kotlinflow.model.OrderBook
import kotlin.math.absoluteValue

class OrderBookAdapter : RecyclerView.Adapter<OrderBookAdapter.OrderBookViewHolder>() {

    private val differ = object : DiffUtil.ItemCallback<OrderBook>() {
        override fun areItemsTheSame(oldItem: OrderBook, newItem: OrderBook) = when {
            newItem.amount > 0 -> oldItem == newItem
            newItem.amount < 0 -> oldItem == newItem
            else -> oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: OrderBook, newItem: OrderBook) = oldItem == newItem
    }
    val asyncDiffer = AsyncListDiffer(this, differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderBookViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun getItemViewType(position: Int): Int {
        return if (asyncDiffer.currentList[position].amount > 0) {
            R.layout.order_book_bid_item
        } else {
            R.layout.order_book_ask_item
        }
    }
    override fun getItemCount(): Int = asyncDiffer.currentList.size

    override fun onBindViewHolder(viewHolder: OrderBookViewHolder, position: Int) {
        viewHolder.bindOrderBookItems(asyncDiffer.currentList[position])
    }

    class OrderBookViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindOrderBookItems(order: OrderBook) = with(OrderBookBidItemBinding.bind(view)) {
            amount.text = order.amount.absoluteValue.toString()
            price.text = order.price.toString()
        }
    }
}
