package art.mohregregs.oddo.Network.Models

class ProductModel(
    var id: Int = -1,
    var name: String = "",
    var price: Double = 0.0,
    var ingredients: List<IngredientModel>? = null
) {
}