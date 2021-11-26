package com.template.wnfxtest.ui.items

import android.os.Bundle
import android.text.Html
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
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.template.models.GoodModel
import com.template.utils.Status
import com.template.wnfxtest.databinding.FragmentItemsBinding
import com.template.wnfxtest.ui.bottomSheetFragment.BottomItemFragment
import com.template.wnfxtest.ui.items.recycler.RVAdapter
import java.lang.Exception

class ItemsFragment : Fragment() {
    private val binding: FragmentItemsBinding by viewBinding(CreateMethod.INFLATE)

    private val itemsViewModel by lazy { ViewModelProvider(this).get(ItemsViewModel::class.java) }

    private val layoutManager by lazy { LinearLayoutManager(requireContext()) }
    private lateinit var adapter: RVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        itemsViewModel.getGoods().observe(viewLifecycleOwner, { resource ->
            resource?.let {
                when(resource.status) {
                    Status.ERROR -> {
                        showOrHideLoadingProcess(hide = false)
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        showOrHideLoadingProcess(hide = false)
                        resource.data?.let { list ->
                            setupRView(list)
                        }

                    }
                    Status.LOADING -> {
                        showOrHideLoadingProcess(hide = true)
                    }
                }
            }
        })

        return binding.root
    }

    private fun setupRView(list: List<GoodModel>) {
        adapter = RVAdapter(list)
        adapter.setOnItemClickListener(object : RVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val currentItem = list[position]
                val bottomFragment = BottomItemFragment(currentItem = currentItem)

                bottomFragment.show(parentFragmentManager, "tag")
            }
        })
        binding.recyclerGoodsList.adapter = adapter
        binding.recyclerGoodsList.layoutManager = layoutManager
    }

    private fun showOrHideLoadingProcess(hide: Boolean) {
        with(binding) {
            if (hide) {
                recyclerGoodsList.visibility = View.GONE
                goodsLoading.visibility = View.VISIBLE
            } else {
                recyclerGoodsList.visibility = View.VISIBLE
                goodsLoading.visibility = View.GONE
            }
        }
    }
}