package com.example.grievifyadmin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.grievifyadmin.dataClass.TicketData
import com.example.grievifyadmin.databinding.TicketLayoutBinding

class ResolvedAdapter(private val context: Context?,
                      private var ArrayList: ArrayList<TicketData>
) :
    RecyclerView.Adapter<ResolvedAdapter.ItemsViewHolder>() {

    inner class ItemsViewHolder(val adapterBinding: TicketLayoutBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding=TicketLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.adapterBinding.textView2.text=ArrayList[position].ticketID
        holder.adapterBinding.textView3.text=ArrayList[position].priority
        holder.itemView.setOnClickListener {
            Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return ArrayList.size
    }

}
