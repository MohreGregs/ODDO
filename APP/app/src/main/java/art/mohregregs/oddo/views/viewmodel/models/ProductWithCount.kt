package art.mohregregs.oddo.views.viewmodel.models

import art.mohregregs.oddo.network.models.ProductModel

class ProductWithCount(
    var product: ProductModel,
    var count: Int = 0,
    var ingredients: List<IngredientWithCount> = listOf()
) {
}