package com.example.grievifyadmin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.grievifyadmin.DescriptionActivity
import com.example.grievifyadmin.StartActivity
import com.example.grievifyadmin.dataClass.UserModel
import com.example.grievifyadmin.databinding.EmployeeLayoutBinding
import com.example.grievifyadmin.databinding.TicketLayoutBinding

class EmployeeListAdapter(private val context: Context?,
                          private var ArrayList: ArrayList<UserModel>
) :
    RecyclerView.Adapter<EmployeeListAdapter.ItemsViewHolder>() {

    inner class ItemsViewHolder(val adapterBinding: EmployeeLayoutBinding) :
        RecyclerView.ViewHolder(adapterBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding=EmployeeLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.adapterBinding.tvName.text=ArrayList[position].name
        holder.adapterBinding.tvEmail.text=ArrayList[position].email

    }

    override fun getItemCount(): Int {
        return ArrayList.size
    }

}
