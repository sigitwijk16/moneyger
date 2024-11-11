package com.gitz.moneyger.feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.gitz.moneyger.MoneygerApplication
import com.gitz.moneyger.R
import com.gitz.moneyger.databinding.FragmentDaftarUangMasukBinding
import com.gitz.moneyger.databinding.FragmentInputUangMasukBinding
import com.gitz.moneyger.model.UangMasuk
import com.gitz.moneyger.utils.Utils.getCurrentFormattedTimestamp
import com.gitz.moneyger.viewmodel.UangMasukViewModel
import com.gitz.moneyger.viewmodel.UangMasukViewModelFactory


class InputUangMasukFragment : Fragment() {

    private var _binding: FragmentInputUangMasukBinding? = null
    private val binding get() = _binding!!

    private val uangMasukViewModel: UangMasukViewModel by viewModels {
        UangMasukViewModelFactory((requireActivity().application as MoneygerApplication).uangMasukRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputUangMasukBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.tvSave.setOnClickListener {
            val kasir = binding.inputPenerima.text.toString()
            val sumber = binding.inputSumber.text.toString()
            val keterangan = binding.inputKeterangan.text.toString()
            val uang = binding.inputJumlah.text.toString().toDouble()

            val uangMasuk =
                UangMasuk(0, getCurrentFormattedTimestamp(), kasir, sumber, keterangan, uang)

            uangMasukViewModel.insert(uangMasuk)

            uangMasukViewModel.insertSuccess.observe(viewLifecycleOwner) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Uang masuk dari ${uangMasuk.sumber}", Toast.LENGTH_SHORT).show()
                    uangMasukViewModel.resetInsertStatus()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}