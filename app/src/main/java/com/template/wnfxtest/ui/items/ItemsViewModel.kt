package com.template.wnfxtest.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.template.data.repository.GoodsRepositoryImpl
import com.template.domain.usecase.GetGoodsUseCase
import com.template.domain.usecase.SortGoodsUseCase
import com.template.utils.Resource
import kotlinx.coroutines.Dispatchers

class ItemsViewModel : ViewModel() {

   // private val _goodsListLiveData = MutableLiveData<List<GoodModel>>().apply {
   //     value = getGoods()
   // }
   // val goodsListLiveData: LiveData<List<GoodModel>> = _goodsListLiveData

    private val goodsRepositoryImpl by lazy { GoodsRepositoryImpl() }
    private val getGoodsUseCase by lazy { GetGoodsUseCase(goodsRepositoryImpl) }

    fun getGoods() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getGoodsUseCase.execute()))
        }  catch(e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Undefined error!"))
        }
    }
}