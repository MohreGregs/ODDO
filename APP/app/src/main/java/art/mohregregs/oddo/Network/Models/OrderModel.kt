package art.mohregregs.oddo.Network.Models

import art.mohregregs.oddo.Network.Enums.Status

class OrderModel(
    var id: Int = -1,
    var status: Status = Status.ORDERED,
    var waiter: WaiterModel = WaiterModel(),
    var table: TableModel?,
    var products: List<OrderProductModel>
) {
}