package art.mohregregs.oddo.network.models.addModels

class AddOrderProductModel(
    var id: Int = -1,
    var productId: Int = -1,
    var count: Int = -1,
    var ingredients: List<AddOrderProductIngredientModel>
) {
}