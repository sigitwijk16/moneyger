package com.gitz.moneyger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitz.moneyger.databinding.ItemUangMasukBinding
import com.gitz.moneyger.model.UangMasuk

class UangMasukAdapter : RecyclerView.Adapter<UangMasukAdapter.UangMasukViewHolder>() {

    private var groupedTransactions: List<List<UangMasuk>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UangMasukViewHolder {
        val binding = ItemUangMasukBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UangMasukViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UangMasukViewHolder, position: Int) {
        val transactionGroup = groupedTransactions[position]
        holder.bind(transactionGroup)
    }

    override fun getItemCount(): Int = groupedTransactions.size

    fun submitList(groupedTransactions: List<List<UangMasuk>>) {
        this.groupedTransactions = groupedTransactions
        notifyDataSetChanged()
    }

    inner class UangMasukViewHolder(private val binding: ItemUangMasukBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transactions: List<UangMasuk>) {
            val totalAmount = transactions.sumOf { it.jumlah }
            binding.tvDate.text = transactions[0].tanggal.substring(0, 10)
            binding.tvTotal.text = "Rp. $totalAmount"

            val transactionAdapter = UangMasukTransactionAdapter()
            binding.rvDateTransactions.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvDateTransactions.adapter = transactionAdapter

            transactionAdapter.submitList(transactions)
        }
    }
}

