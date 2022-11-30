package art.mohregregs.oddo.views.viewmodel

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import art.mohregregs.oddo.network.ApiEndpoint
import art.mohregregs.oddo.network.models.ProductModel

class OrderViewModel: ViewModel() {
    private var _products = MutableLiveData(listOf<ProductModel>())
    val products: LiveData<List<ProductModel>> = _products

    fun getProducts(context: Context){
        ApiEndpoint.ApiEndpoint.getProducts(context){
            _products = (it ?: MutableLiveData(listOf<ProductModel>())) as MutableLiveData<List<ProductModel>>
        }
    }
}