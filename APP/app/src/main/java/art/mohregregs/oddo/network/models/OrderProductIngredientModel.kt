package art.mohregregs.oddo.network.models

class OrderProductIngredientModel(var ingredient: IngredientModel, var count: Int) {
    var id: Int = 0

    companion object OrderProductIngredientModel{
        var counter: Int = 0
    }

    init {
        counter++
        id = counter
    }
}