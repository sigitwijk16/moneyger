package com.gitz.moneyger.feature

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitz.moneyger.MoneygerApplication
import com.gitz.moneyger.R
import com.gitz.moneyger.adapter.UangMasukAdapter
import com.gitz.moneyger.databinding.FragmentDaftarUangMasukBinding
import com.gitz.moneyger.model.UangMasuk
import com.gitz.moneyger.utils.Utils.getCurrentFormattedTimestamp
import com.gitz.moneyger.viewmodel.UangMasukViewModel
import com.gitz.moneyger.viewmodel.UangMasukViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class DaftarUangMasukFragment : Fragment() {

    private var _binding: FragmentDaftarUangMasukBinding? = null
    private val binding get() = _binding!!

    private val uangMasukViewModel: UangMasukViewModel by viewModels {
        UangMasukViewModelFactory((requireActivity().application as MoneygerApplication).uangMasukRepository)
    }

    private lateinit var uangMasukAdapter: UangMasukAdapter

    private val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDaftarUangMasukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setDefaultDateRange()

        binding.entryDateLayout.setOnClickListener {
            showDatePickerDialog()
        }

        binding.tambahUangMasuk.setOnClickListener {
            findNavController().navigate(R.id.action_daftarUangMasukFragment_to_inputUangMasukFragment)
        }
    }

    fun deleteTransaction(uangMasuk: UangMasuk) {
        uangMasukViewModel.delete(uangMasuk)
    }

    fun insertTransaction() {
        val currentFormattedTimestamp = "2024-11-09 22:00:00"

        val sampleData = listOf(
            UangMasuk(tanggal = currentFormattedTimestamp, kasir = "Kasir 1", sumber = "Bos", keterangan = "Initial funding", jumlah = 500000.0),
            UangMasuk(tanggal = currentFormattedTimestamp, kasir = "Kasir 2", sumber = "Investasi", keterangan = "Additional capital", jumlah = 300000.0),
            UangMasuk(tanggal = currentFormattedTimestamp, kasir = "Kasir 1", sumber = "Bos", keterangan = "Initial funding", jumlah = 500000.0),
            UangMasuk(tanggal = currentFormattedTimestamp, kasir = "Kasir 2", sumber = "Investasi", keterangan = "Additional capital", jumlah = 300000.0),
            UangMasuk(tanggal = currentFormattedTimestamp, kasir = "Kasir 1", sumber = "Bos", keterangan = "Initial funding", jumlah = 500000.0),
            UangMasuk(tanggal = currentFormattedTimestamp, kasir = "Kasir 2", sumber = "Investasi", keterangan = "Additional capital", jumlah = 300000.0)
        )

        sampleData.forEach { uangMasuk ->
            uangMasukViewModel.insert(uangMasuk)
        }
    }

    private fun setupRecyclerView() {
        uangMasukAdapter = UangMasukAdapter()
        binding.daftarUangMasuk.layoutManager = LinearLayoutManager(requireContext())
        binding.daftarUangMasuk.adapter = uangMasukAdapter
    }


    private fun observeAllTransactions() {
        uangMasukViewModel.allUangMasuk.observe(viewLifecycleOwner) { transaksi ->
            if (transaksi.isNullOrEmpty()) {
                binding.daftarUangMasuk.visibility = View.GONE
                binding.noDataMessage.visibility = View.VISIBLE
            } else {
                binding.daftarUangMasuk.visibility = View.VISIBLE
                binding.noDataMessage.visibility = View.GONE

                val groupedTransactions = transaksi.groupBy {
                    it.tanggal.substring(0, 10)
                }.toList()

                val transactionsByDate = groupedTransactions.map { it.second }

                val sortedTransactionsByDate = transactionsByDate.sortedBy {
                    it.first().tanggal.substring(0, 10)
                }

                uangMasukAdapter.submitList(sortedTransactionsByDate)
            }
        }
    }

    private fun setDefaultDateRange() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val startDate = calendar.time
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))

        val endDate = calendar.time

        val formattedStartDate = dateTimeFormat.format(startDate) + " 00:00:00"
        val formattedEndDate = dateTimeFormat.format(endDate) + " 23:59:59"

        binding.entryDate.text = "${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}"

        observeTransactionsByDateRange(formattedStartDate, formattedEndDate)
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->

            calendar.set(year, month, dayOfMonth)

            val selectedStartDate = calendar.time
            val queryStartDate = Date(calendar.time.time - 86400000)

            val endDatePickerDialog = DatePickerDialog(requireContext(), { _, endYear, endMonth, endDayOfMonth ->

                calendar.set(endYear, endMonth, endDayOfMonth)

                val selectedEndDate = calendar.time
                val queryEndDate = Date(calendar.time.time + 86400000)

                val formattedStartDate = dateTimeFormat.format(queryStartDate) + " 00:00:00"
                val formattedEndDate = dateTimeFormat.format(queryEndDate) + " 23:59:59"

                observeTransactionsByDateRange(formattedStartDate, formattedEndDate)

                val formattedStartDateForDisplay = dateFormat.format(selectedStartDate)
                val formattedEndDateForDisplay = dateFormat.format(selectedEndDate)

                binding.entryDate.text = "$formattedStartDateForDisplay - $formattedEndDateForDisplay"
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

            endDatePickerDialog.show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }



    private fun observeTransactionsByDateRange(startDate: String, endDate: String) {

        uangMasukViewModel.getUangMasukByDate(startDate, endDate).observe(viewLifecycleOwner, Observer { transaksi ->
            if (transaksi.isNullOrEmpty()) {
                binding.daftarUangMasuk.visibility = View.GONE
                binding.noDataMessage.visibility = View.VISIBLE
            } else {
                binding.daftarUangMasuk.visibility = View.VISIBLE
                binding.noDataMessage.visibility = View.GONE

                val groupedTransactions = transaksi.groupBy {
                    it.tanggal.substring(0, 10)
                }.toList()

                val transactionsByDate = groupedTransactions.map { it.second }

                val sortedTransactionsByDate = transactionsByDate.sortedBy {
                    it.first().tanggal.substring(0, 10)
                }

                uangMasukAdapter.submitList(sortedTransactionsByDate)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
