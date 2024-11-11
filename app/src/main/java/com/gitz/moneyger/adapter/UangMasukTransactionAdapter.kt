package com.gitz.moneyger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gitz.moneyger.databinding.ItemTransactionBinding
import com.gitz.moneyger.model.UangMasuk

class UangMasukTransactionAdapter : RecyclerView.Adapter<UangMasukTransactionAdapter.TransactionViewHolder>() {

    private var transactionList: List<UangMasuk> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int = transactionList.size

    fun submitList(transactions: List<UangMasuk>) {
        this.transactionList = transactions
        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: UangMasuk) {
            binding.tvTerimaDari.text = "Dari ${transaction.sumber} ke ${transaction.kasir}"
            val formattedDate = transaction.tanggal
            binding.tvJam.text = formattedDate.substring(11, 16)
            binding.tvKeterangan.text = transaction.keterangan
            binding.tvJumlah.text = "Rp. ${transaction.jumlah}"
        }
    }
}
