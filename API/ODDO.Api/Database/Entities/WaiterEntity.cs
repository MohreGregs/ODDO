namespace ODDOApi.Database.Entities;

public class WaiterEntity: BaseEntity
{
    public string Name { get; set; }
    public string Password { get; set; }

    public virtual ISet<OrderEntity> Orders { get; set; } = new HashSet<OrderEntity>();
}