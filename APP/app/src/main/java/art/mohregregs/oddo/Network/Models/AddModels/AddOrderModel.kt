package art.mohregregs.oddo.Network.Models.AddModels

class AddOrderModel(
    var id: Int = -1,
    var tableId: Int = -1,
    var waiterId: Int?,
    var products: List<AddOrderProductModel>
) {
}