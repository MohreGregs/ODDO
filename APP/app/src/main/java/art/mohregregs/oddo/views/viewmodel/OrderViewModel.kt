package art.mohregregs.oddo.views.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import art.mohregregs.oddo.network.ApiEndpoint
import art.mohregregs.oddo.network.models.IngredientModel
import art.mohregregs.oddo.network.models.OrderProductIngredientModel
import art.mohregregs.oddo.network.models.OrderProductModel
import art.mohregregs.oddo.network.models.ProductModel
import art.mohregregs.oddo.network.models.addModels.AddOrderProductIngredientModel
import art.mohregregs.oddo.views.viewmodel.models.IngredientWithCount
import art.mohregregs.oddo.views.viewmodel.models.ProductWithCount

class OrderViewModel: ViewModel() {
    private var _products = MutableLiveData(listOf<ProductWithCount>())
    val products: LiveData<List<ProductWithCount>> = _products

    private var _productsToOrder = MutableLiveData(listOf<OrderProductModel>())
    val productsToOrder: LiveData<List<OrderProductModel>> = _productsToOrder

    fun getProducts(context: Context){
        ApiEndpoint.getProducts(context){ data ->
            _products.value = listOf()
            data?.forEach { product ->
                var ingredients = listOf<IngredientWithCount>()
                product.ingredients?.forEach {
                    ingredients = ingredients.plus(IngredientWithCount(it, 0))
                }
                _products.value = _products.value!!.plus(ProductWithCount(product, 0, ingredients))
            }
        }
    }

    fun addProductToOrder(product: ProductWithCount){
        if(_productsToOrder.value?.find { x -> x.product == product.product && x.product.ingredients == x.ingredients } != null){
            return
        }

        var ingredients = listOf<OrderProductIngredientModel>()
        var orderIngredients = ingredients.filter { x -> x.count > 0 }

        orderIngredients.forEach { ingredient ->
            ingredients = ingredients.plus(OrderProductIngredientModel(ingredient = ingredient.ingredient, count = ingredient.count))
        }

        _productsToOrder.value = _productsToOrder.value!!.plus(OrderProductModel(product = product.product, count = product.count, ingredients = ingredients))

    }

    fun resetProductCount(productId: Int){
        val product = _products.value?.find { x -> x.product.id == productId } ?: return

        product.count = 0
        product.ingredients.forEach { ingredient ->
            ingredient.count = 0
        }
    }

//    fun addIngredientToProduct(productId: Int, ingredient: IngredientModel){
//        val product = _productsToOrder.value!!.find { x-> x.id == productId }
//        if(isIngredientInProduct(product!!.ingredients, ingredient.id)){
//            val extra = product.ingredients.find { x-> x.ingredient.id == ingredient.id }
//            extra!!.count++
//        }else{
//            product.ingredients = product.ingredients.plus(OrderProductIngredientModel(ingredient = ingredient, count = 1))
//        }
//    }
//
//    private fun isProductInOrder(productId: Int): Boolean = _productsToOrder.value!!.find { x-> x.id == productId } != null

//    private fun isIngredientInProduct(ingredients: List<OrderProductIngredientModel>, extraId: Int): Boolean = ingredients.find { x -> x.ingredient.id == extraId} != null

    fun setAmountOfProduct(productId: Int, amount: Int){
        var newProductToOrderList: ArrayList<ProductWithCount> = _products.value as ArrayList<ProductWithCount>
        var product: ProductWithCount? =
            newProductToOrderList.find { x -> x.product.id == productId } ?: return

        product!!.count = amount
        _products.value = listOf()
        _products.value = _products.value?.plus(newProductToOrderList)
    }

    fun setAmountOfExtra(productId: Int, extraId: Int, amount: Int){
        var newProductToOrderList: ArrayList<ProductWithCount> = _products.value as ArrayList<ProductWithCount>
        var product: ProductWithCount? =
            newProductToOrderList.find { x -> x.product.id == productId } ?: return

        var extra: IngredientWithCount? =
            product?.ingredients?.find { x -> x.ingredient.id == extraId } ?: return

        extra!!.count = amount
        _products.value = listOf()
        _products.value = _products.value?.plus(newProductToOrderList)
    }

    fun getTotalAmountOfProduct(product: ProductWithCount): Double{
        var ingredientPrice = 0.0
        product.ingredients.forEach { ingredient ->
            ingredientPrice += ingredient.ingredient.price * ingredient.count
        }
        return product.product.price * product.count + ingredientPrice
    }
}