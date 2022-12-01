package art.mohregregs.oddo.network.models

class OrderProductIngredientModel(
    var id: Int = -1,
    var ingredient: IngredientModel = IngredientModel(),
    var count: Int = -1
) {
}