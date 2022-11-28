using ODDO.Data.Enums;

namespace ODDO.Data.Models; 

public class OrderModel: BaseModel {
    public Status Status { get; set; }
    public WaiterModel Waiter{ get; set; }
    public TableModel Table { get; set; }
    
    public HashSet<OrderProductModel> Products { get; set; }
}