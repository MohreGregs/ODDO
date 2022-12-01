package art.mohregregs.oddo.network.models

class OrderProductModel(
    var count: Int = 0,
    var product: ProductModel,
    var ingredients: List<OrderProductIngredientModel>
) {
    var id: Int = -1

    companion object OrderProduct{
        var counter: Int = 0
    }

    init {
        counter++
        id = counter
    }
}