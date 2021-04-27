package com.leandroquintans.lazywallet.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leandroquintans.lazywallet.R
import com.leandroquintans.lazywallet.databinding.FragmentPaymentListBinding
import com.leandroquintans.lazywallet.databinding.FragmentWalletBinding

class PaymentListFragment : Fragment() {
    private lateinit var binding: FragmentPaymentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentListBinding.inflate(inflater, container, false)
        return binding.root
    }
}