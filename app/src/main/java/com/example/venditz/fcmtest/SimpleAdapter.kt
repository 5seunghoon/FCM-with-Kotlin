package com.example.venditz.fcmtest

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.simple_data_item_layout.view.*

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.Holder>() {

    private var simpleDataList: MutableList<SimpleData> = mutableListOf()

    inner class Holder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.simple_data_item_layout, parent, false)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent)

    override fun getItemCount() = simpleDataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val simpleData = simpleDataList[position]

        holder.itemView.run {
            simple_data_item_title_text_view.text = String.format("%s", simpleData.title)
            simple_data_item_title_text_view.setOnClickListener {
                Snackbar.make(this, "TITLE : ${simpleData.title}", Snackbar.LENGTH_LONG).show()
            }
            simple_data_item_message_text_view.text = String.format("%s", simpleData.message)
            simple_data_item_message_text_view.setOnClickListener {
                Snackbar.make(this, "MESSAGE : ${simpleData.message}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun addData(data: SimpleData, notifyInsert: Boolean) {
        simpleDataList.add(data)
        if (notifyInsert) this.notifyItemInserted(simpleDataList.size - 1)
    }
}
