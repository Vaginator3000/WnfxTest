package com.template.wnfxtest.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.models.GoodModelCart
import com.template.wnfxtest.databinding.FragmentCartBinding
import com.template.wnfxtest.ui.cart.recycler.CartRVAdapter
import com.template.wnfxtest.ui.items.recycler.ItemsRVAdapter

class CartFragment : Fragment() {
    private val binding: FragmentCartBinding by viewBinding(CreateMethod.INFLATE)

    private val cartViewModel by lazy { ViewModelProvider(this).get(CartViewModel::class.java) }

    private val layoutManager by lazy { LinearLayoutManager(requireContext()) }
    private var adapter: CartRVAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cartIsEmpty = cartViewModel.getGoodsInCart() == null
        showOrHideRecycler(cartIsEmpty)

        cartViewModel.goodsInCart.observe(viewLifecycleOwner, { goodsInCart ->
            if (!cartIsEmpty) {
                setupRView(goodsInCart)
            }
        })
        cartViewModel.getTotalPrice().observe(viewLifecycleOwner, { totalPrice ->
            setTotalPrice(totalPrice)
        })

        binding.cartFragmentBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Nothing!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun setTotalPrice(price: Int) {
        binding.cartFragmentBtn.text = price.toString()
    }

    private fun setupRView(goodsInCart: List<GoodModelCart>) {
        adapter = CartRVAdapter(goodsInCart as MutableList<GoodModelCart>)
        binding.cartFragmentRecycler.adapter = adapter
        binding.cartFragmentRecycler.layoutManager = layoutManager
    }

    private fun showOrHideRecycler(cartIsEmpty: Boolean) {
        with(binding) {
            if (cartIsEmpty) {
                cartFragmentRecycler.visibility = View.GONE
                cartFragmentTv.visibility = View.VISIBLE
            } else {
                cartFragmentRecycler.visibility = View.VISIBLE
                cartFragmentTv.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.let {
            cartViewModel.updateList(adapter!!.goodsInCart)
        }
    }
}