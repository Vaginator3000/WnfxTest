package com.template.wnfxtest.ui.bottomSheetFragment

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.template.models.GoodModel
import com.template.wnfxtest.R
import com.template.wnfxtest.databinding.BottomSheetFragmentBinding
import java.lang.Exception

private const val COLLAPSED_HEIGHT = 250

class BottomItemFragment(private val currentItem: GoodModel) : BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetFragmentBinding

    // Тема с закругленными углами
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            BottomSheetFragmentBinding.bind(inflater.inflate(R.layout.bottom_sheet_fragment, container))
        return binding.root
    }

    private fun setData() {
        Picasso.get().load(currentItem.imageurl).into(binding.bSheetImg, object : Callback {
            override fun onSuccess() {
            }

            override fun onError(e: Exception?) {
                Toast.makeText(this@BottomItemFragment.context, e.toString(), Toast.LENGTH_SHORT).show()
            }

        })
        binding.bSheetPrice.text = "${currentItem.price}₽"
        binding.bSheetRating.text = "${currentItem.rating}★"
        binding.bSheetDesc.text = Html.fromHtml(currentItem.fullInfo)
        //не могу понять как подтянуть сюда строковый ресурс
    }

    override fun onStart() {
        super.onStart()

        setData()

        // Плотность понадобится нам в дальнейшем
        val density = requireContext().resources.displayMetrics.density
        dialog?.let {
            // Находим bottomSheet и достаём из него Behaviour
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // Выставляем высоту для состояния collapsed и выставляем состояние collapsed
            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    with(binding) {
                        // Нужен только положительный оффсет, при отрицательном фрагмент скроется
                        if (slideOffset > 0) {
                            // Делаем "свёрнутый" layout более прозрачным
                            layoutCollapsed.alpha = 1 - 2 * slideOffset
                            // И в то же время делаем "расширенный layout" менее прозрачным
                            layoutExpanded.alpha = slideOffset * slideOffset

                            // Когда оффсет превышает половину, мы скрываем collapsed layout и делаем видимым expanded
                            if (slideOffset > 0.5) {
                                layoutCollapsed.visibility = View.GONE
                                layoutExpanded.visibility = View.VISIBLE
                            }

                            // Если же оффсет меньше половины, а expanded layout всё ещё виден, то нужно скрывать его и показывать collapsed
                            if (slideOffset < 0.5 && binding.layoutExpanded.visibility == View.VISIBLE) {
                                layoutCollapsed.visibility = View.VISIBLE
                                layoutExpanded.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            })
        }
    }

}