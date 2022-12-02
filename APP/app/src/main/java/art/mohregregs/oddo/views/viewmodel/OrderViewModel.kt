package art.mohregregs.oddo.views.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import art.mohregregs.oddo.network.ApiEndpoint
import art.mohregregs.oddo.network.models.*
import art.mohregregs.oddo.network.models.addModels.AddOrderModel
import art.mohregregs.oddo.network.models.addModels.AddOrderProductIngredientModel
import art.mohregregs.oddo.network.models.addModels.AddOrderProductModel
import art.mohregregs.oddo.views.viewmodel.models.IngredientWithCount
import art.mohregregs.oddo.views.viewmodel.models.ProductWithCount

class OrderViewModel: ViewModel() {
    private var _products = MutableLiveData(listOf<ProductWithCount>())
    val products: LiveData<List<ProductWithCount>> = _products

    private var _productsToOrder = MutableLiveData(listOf<OrderProductModel>())
    val productsToOrder: LiveData<List<OrderProductModel>> = _productsToOrder

    private var _currentOrdersOfTable = MutableLiveData(listOf<OrderModel>())
    val currentOrdersOfTable: LiveData<List<OrderModel>> = _currentOrdersOfTable

    fun resetViewModel() {
        _products.value = listOf()
        _productsToOrder.value = listOf()
        _currentOrdersOfTable.value = listOf()
    }

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

    fun addOrder(context: Context){
        ApiEndpoint.addOrder(context, getOrderBody()){
            if (it != null) {
                _currentOrdersOfTable.value = _currentOrdersOfTable.value!!.plus(it)
            }
        }
    }

    fun getOrderStatusesByTableId(context: Context){
        ApiEndpoint.getStatusByTableId(context, 1){ stati ->
            stati?.forEach { status ->
                val temp = _currentOrdersOfTable.value

                val order = temp?.find { x -> x.id == status.id } ?: return@forEach
                order.status = status.status

                _currentOrdersOfTable.value = listOf()
                _currentOrdersOfTable.value = _currentOrdersOfTable.value!!.plus(temp)
            }
        }
    }

    private fun getOrderBody(): AddOrderModel{
        var products: List<AddOrderProductModel> = listOf()

        _productsToOrder.value?.forEach { product ->
            var ingredients: List<AddOrderProductIngredientModel> = listOf()

            product.ingredients.forEach { ingredient ->
                ingredients = ingredients.plus(AddOrderProductIngredientModel(ingredientId = ingredient.ingredient.id, count = ingredient.count))
            }

            products = products.plus(AddOrderProductModel(productId = product.product.id, count = product.count, ingredients = ingredients))
        }
        return AddOrderModel(tableId = 1, products = products)
    }

    fun addProductToOrder(product: ProductWithCount){
        if(_productsToOrder.value?.find { x -> x.product == product.product && x.product.ingredients == x.ingredients } != null){
            return
        }

        var ingredients = listOf<OrderProductIngredientModel>()
        var orderIngredients = product.ingredients.filter { x -> x.count > 0 }

        orderIngredients.forEach { ingredient ->
            ingredients = ingredients.plus(OrderProductIngredientModel(ingredient = ingredient.ingredient, count = ingredient.count))
        }

        _productsToOrder.value = _productsToOrder.value!!.plus(OrderProductModel(product = product.product, count = product.count, ingredients = ingredients))
    }

    fun removeProductFromOrder(productModel: OrderProductModel): Boolean{
        if(!_productsToOrder.value!!.contains(productModel)) return false

        _productsToOrder.value = _productsToOrder.value!!.minus(productModel)

        return _productsToOrder.value!!.isEmpty()
    }

    // Ingredient is not removed
    fun removeExtraFromProduct(product: OrderProductModel, ingredientModel: OrderProductIngredientModel){
        val productList  = _productsToOrder.value
        val product = productList?.find { x -> x.id == product.id} ?: return

        val ingredientToRemove = product.ingredients.find { x -> x.id == ingredientModel.id }  ?: return

        product.ingredients = product.ingredients.minus(ingredientToRemove)

        _productsToOrder.value = listOf()
        _productsToOrder.value = _productsToOrder.value!!.plus(productList)
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

    fun getTotalOrderAmount(): Double{
        var totalAmount = 0.0
        _productsToOrder.value!!.forEach { product ->
            var ingredientPrice = 0.0
            product.ingredients.forEach { ingredient ->
                ingredientPrice += ingredient.ingredient.price * ingredient.count
            }
            totalAmount += product.product.price * product.count + ingredientPrice
        }

        return totalAmount
    }
}