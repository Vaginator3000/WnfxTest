package com.template.wnfxtest.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.models.GoodModelCart
import com.template.wnfxtest.databinding.FragmentCartBinding
import com.template.wnfxtest.ui.cart.recycler.CartRVAdapter

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

        if (!cartIsEmpty) {
            cartViewModel.goodsInCart.observe(viewLifecycleOwner, { goodsInCart ->
                setupRView(goodsInCart)

            })
            cartViewModel.totalPrice.observe(viewLifecycleOwner, { totalPrice ->
                setTotalPrice(totalPrice)
            })

            setOnClicks()
        }


        return binding.root
    }

    //Все клики
    private fun setOnClicks() {
        binding.cartFragmentBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Nothing!", Toast.LENGTH_SHORT).show()
        }
    }

    //Устанавливаем итоговую цену на кнопку
    private fun setTotalPrice(price: Int) {
        binding.cartFragmentBtn.text = "$price ₽"
    }

    //установили адаптер на RV
    private fun setupRView(goodsInCart: List<GoodModelCart>) {
        adapter = CartRVAdapter(goodsInCart as MutableList<GoodModelCart>)
        setRVEvents()
        binding.cartFragmentRecycler.adapter = adapter
        binding.cartFragmentRecycler.layoutManager = layoutManager
    }

    private fun setRVEvents() {
        //увеличение количества
        adapter?.setIncreaseAmountBtnClickListener (object : CartRVAdapter.OnClickListener {
            override fun onClick(position: Int) {
                cartViewModel.incGoodAmountByIndex(index = position)
            }
        })

        //уменьшение количества
        adapter?.setReduceAmountBtnClickListener (object : CartRVAdapter.OnClickListener {
            override fun onClick(position: Int) {
                cartViewModel.redGoodAmountByIndex(index = position)
            }
        })

        var simpleCallBack =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val index = viewHolder.absoluteAdapterPosition
                    cartViewModel.removeGoodByIndex(index)
                    adapter?.notifyItemRemoved(index)
                }

            }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(binding.cartFragmentRecycler)
    }

    //показываем/скрываем RV и текст
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
       //     cartViewModel.updateList(adapter!!.goodsInCart)
        }
    }
}