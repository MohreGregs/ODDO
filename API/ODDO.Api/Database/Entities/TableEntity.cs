namespace ODDOApi.Database.Entities;

public class TableEntity: BaseEntity {
    public virtual ISet<OrderEntity> Orders { get; set; } = new HashSet<OrderEntity>();
}