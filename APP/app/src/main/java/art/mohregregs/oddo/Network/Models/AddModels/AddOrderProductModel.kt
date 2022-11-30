package art.mohregregs.oddo.Network.Models.AddModels

class AddOrderProductModel(
    var id: Int = -1,
    var productId: Int = -1,
    var count: Int = -1,
    var ingredients: List<AddOrderProductIngredientModel>
) {
}