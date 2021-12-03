package com.template.wnfxtest.ui.items

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.template.data.repository.GoodsRepositoryImpl
import com.template.data.repository.SharedPrefsCartRepositoryImpl
import com.template.domain.usecase.AddToCartUseCase
import com.template.domain.usecase.GetGoodsUseCase
import com.template.models.GoodModel
import com.template.models.GoodModelCart
import com.template.utils.Resource
import kotlinx.coroutines.Dispatchers

class ItemsViewModel(app: Application) : AndroidViewModel(app) {

   // private val _goodsListLiveData = MutableLiveData<List<GoodModel>>().apply {
   //     value = getGoods()
   // }
   // val goodsListLiveData: LiveData<List<GoodModel>> = _goodsListLiveData

    private val goodsRepositoryImpl by lazy { GoodsRepositoryImpl() }
    private val getGoodsUseCase by lazy { GetGoodsUseCase(goodsRepositoryImpl) }
    private val sharedPrefsCartRepositoryImpl by lazy { SharedPrefsCartRepositoryImpl(getApplication<Application>().applicationContext) }
    private val addToCartUseCase by lazy { AddToCartUseCase(sharedPrefsCartRepositoryImpl) }

    fun getGoods() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getGoodsUseCase.execute()))
        }  catch(e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Undefined error!"))
        }
    }

    fun addToCart(goodToCart: GoodModel) {
        addToCartUseCase
            .execute(goodItem =
                GoodModelCart(
                    good = goodToCart,
                    amount = 1
                )
        )
    }
}