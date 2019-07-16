package com.tony.kotlin.widgt.dialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tony.kotlin.widgt.R

/**
 * @author tony
 */
class YTDialogRecyclerAdapter(private val context: Context, private val data: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(string: String, position: Int)
    }

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemText = view.findViewById<TextView>(R.id.layout_dialog_list_item_text)!!
    }

    private var itemClickListener: OnItemClickListener? = null
    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_dialog_list_item, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh: ItemHolder = holder as ItemHolder
        vh.itemText.text = data[position]

        vh.itemView.setOnClickListener { itemClickListener?.onItemClick(data.get(position), position) }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

}