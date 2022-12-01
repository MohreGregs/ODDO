package art.mohregregs.oddo.views.viewmodel.models

import art.mohregregs.oddo.network.models.IngredientModel

class IngredientWithCount(
    var ingredient: IngredientModel = IngredientModel(),
    var count: Int = 0
) {
}