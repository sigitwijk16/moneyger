package com.gitz.moneyger.feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gitz.moneyger.R
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gitz.moneyger.databinding.FragmentDaftarUangMasukBinding
import com.gitz.moneyger.viewmodel.UangMasukViewModel

class DaftarUangMasukFragment : Fragment() {

    private var _binding: FragmentDaftarUangMasukBinding? = null
    private val binding get() = _binding!!
    private val uangMasukViewModel: UangMasukViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarUangMasukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
