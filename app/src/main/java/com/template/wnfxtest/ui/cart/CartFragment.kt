package com.template.wnfxtest.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.wnfxtest.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private val binding: FragmentCartBinding by viewBinding()

    private val cartViewModel by lazy { ViewModelProvider(this).get(CartViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = binding.textDashboard
        cartViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }
}