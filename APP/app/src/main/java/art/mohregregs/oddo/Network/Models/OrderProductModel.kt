package art.mohregregs.oddo.Network.Models

class OrderProductModel(
    var id: Int = -1,
    var count: Int = 0,
    var product: ProductModel,
    var ingredients: List<OrderProductIngredientModel>
) {
}