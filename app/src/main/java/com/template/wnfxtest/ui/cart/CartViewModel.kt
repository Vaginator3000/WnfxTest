package com.template.wnfxtest.ui.cart

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.template.data.repository.SharedPrefsCartRepositoryImpl
import com.template.domain.usecase.AddToCartUseCase
import com.template.domain.usecase.GetGoodsInCartUseCase
import com.template.domain.usecase.RemoveFromCartUseCase
import com.template.models.GoodModelCart
import com.template.utils.Resource
import kotlinx.coroutines.Dispatchers


class CartViewModel(app: Application) : AndroidViewModel(app) {

    private val _goodsInCart = MutableLiveData<List<GoodModelCart>>()
    val goodsInCart: LiveData<List<GoodModelCart>> = _goodsInCart

    private val sharedPrefsCartRepositoryImpl by lazy { SharedPrefsCartRepositoryImpl(getApplication<Application>().applicationContext) }
    private val removeFromCartUseCase by lazy { RemoveFromCartUseCase(sharedPrefsCartRepositoryImpl) }
    private val addToCartUseCase by lazy { AddToCartUseCase(sharedPrefsCartRepositoryImpl) }
    private val getGoodsInCartUseCase by lazy { GetGoodsInCartUseCase(sharedPrefsCartRepositoryImpl) }

    init {
        _goodsInCart.value = getGoodsInCartUseCase.execute()

    }

    private fun updateLiveData() {
        _goodsInCart.value = getGoodsInCartUseCase.execute()
    }

    fun removeGood(goodToRemove: GoodModelCart) {
        removeFromCartUseCase.execute(goodItem = goodToRemove)
        updateLiveData()
    }

    fun addGood(goodToAdd: GoodModelCart) {
        addToCartUseCase.execute(goodItem = goodToAdd)
        updateLiveData()
    }

    fun updateList(newGoods: List<GoodModelCart>) {
        //обновляю, т.к. при измененном количестве, _goodsInCart.value становится такой же, как и newGoods
        updateLiveData()

        _goodsInCart.value?.forEach { goodItem ->
            removeGood(goodItem)
        }
        newGoods.forEach {  goodItem ->
            addGood(goodItem)
        }
    }

    private fun calcTotalPrice(): Int {
        if (_goodsInCart.value == null) return 0

        return _goodsInCart.value!!.map { it.amount * (it.good.price ?: 0) }.fold(0) { sum, i -> sum + i }
    }

    fun getTotalPrice() = liveData(Dispatchers.IO) {
        emit(calcTotalPrice())
    }

    fun getGoodsInCart(): List<GoodModelCart>? {
        return getGoodsInCartUseCase.execute()
        //(_goodsInCart.value as MutableList<GoodModel>).remove(goodToRemove)
    }
}
