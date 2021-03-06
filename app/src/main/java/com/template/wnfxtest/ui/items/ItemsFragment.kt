package com.template.wnfxtest.ui.items

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.models.GoodModel
import com.template.utils.SortBy
import com.template.utils.Status
import com.template.wnfxtest.R
import com.template.wnfxtest.databinding.FragmentItemsBinding
import com.template.wnfxtest.ui.bottomSheetFragment.BottomItemFragment
import com.template.wnfxtest.ui.items.recycler.ItemsRVAdapter

class ItemsFragment : Fragment() {
    private val binding: FragmentItemsBinding by viewBinding(CreateMethod.INFLATE)

    private val itemsViewModel by lazy { ViewModelProvider(this).get(ItemsViewModel::class.java) }

    private val layoutManager by lazy { LinearLayoutManager(requireContext()) }
    private lateinit var adapter: ItemsRVAdapter

    private var goodsList: List<GoodModel>? = null

    //Добавил кнопку для выбора сортировки
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort_by_up_price -> sortRView(sortBy = SortBy.UP_PRICE)
            R.id.sort_by_down_price -> sortRView(sortBy = SortBy.DOWN_PRICE)
            R.id.sort_by_up_rating -> sortRView(sortBy = SortBy.UP_RATING)
            R.id.sort_by_down_rating -> sortRView(sortBy = SortBy.DOWN_RATING)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

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
                            goodsList = list
                            setupRView(goodsList!!)
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
        adapter = ItemsRVAdapter(list)
        adapter.setOnItemClickListener(object : ItemsRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val currentItem = list[position]
                val bottomFragment = BottomItemFragment(currentItem = currentItem)

                bottomFragment.show(parentFragmentManager, "tag")
            }
        })
        adapter.setOnAddToCartClickListener(object : ItemsRVAdapter.OnClickListener {
            override fun onClick(position: Int) {
                val currentItem = list[position]
                itemsViewModel.addToCart(currentItem)
            }
        })
        binding.itemsFragmentRecycler.adapter = adapter
        binding.itemsFragmentRecycler.layoutManager = layoutManager
    }

    // оставил сортировку здесь, т.к. не знаю где она по правильному долэна находиться
    // в принципе, сортировка идет только для пользователя, только в самом RV, так что пусть остается
    private fun sortRView(sortBy: SortBy) {
        goodsList?.run {
            val sortedList =
                when (sortBy) {
                    SortBy.UP_PRICE -> goodsList!!.sortedBy { it.price }
                    SortBy.DOWN_PRICE -> goodsList!!.sortedByDescending { it.price }
                    SortBy.UP_RATING -> goodsList!!.sortedBy { it.rating }
                    SortBy.DOWN_RATING -> goodsList!!.sortedByDescending { it.rating }
                }
            setupRView(sortedList)
        }
    }

    private fun showOrHideLoadingProcess(hide: Boolean) {
        with(binding) {
            if (hide) {
                itemsFragmentRecycler.visibility = View.GONE
                itemsFragmentLoading.visibility = View.VISIBLE
            } else {
                itemsFragmentRecycler.visibility = View.VISIBLE
                itemsFragmentLoading.visibility = View.GONE
            }
        }
    }
}