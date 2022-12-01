package art.mohregregs.oddo.network.models

import art.mohregregs.oddo.network.enums.Status

class OrderModel(
    var id: Int = -1,
    var status: Status = Status.ORDERED,
    var waiter: WaiterModel = WaiterModel(),
    var table: TableModel?,
    var products: List<OrderProductModel>
) {
}