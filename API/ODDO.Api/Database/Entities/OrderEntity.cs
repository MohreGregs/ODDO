using ODDO.Data.Enums;

namespace ODDOApi.Database.Entities;

public class OrderEntity: BaseEntity {
    public Status Status { get; set; }
    public virtual WaiterEntity Waiter{ get; set; }
    public virtual TableEntity Table { get; set; }

    public virtual ISet<OrderProductEntity> Products { get; set; } = new HashSet<OrderProductEntity>();
}