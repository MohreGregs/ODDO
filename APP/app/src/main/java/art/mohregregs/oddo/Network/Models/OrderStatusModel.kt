package art.mohregregs.oddo.Network.Models

import art.mohregregs.oddo.Network.Enums.Status

class OrderStatusModel(
    var id: Int = -1,
    var status: Status = Status.ORDERED
) {

}