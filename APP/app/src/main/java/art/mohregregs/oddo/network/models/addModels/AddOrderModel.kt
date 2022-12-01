package art.mohregregs.oddo.network.models.addModels

class AddOrderModel(
    var id: Int = -1,
    var tableId: Int = -1,
    var waiterId: Int?,
    var products: List<AddOrderProductModel>
) {
}