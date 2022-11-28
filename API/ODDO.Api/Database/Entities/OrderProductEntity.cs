namespace ODDOApi.Database.Entities;

public class OrderProductEntity: BaseEntity
{
    public int Count { get; set; }
    public virtual ProductEntity Product { get; set; }
    public virtual OrderEntity Order { get; set; }

    public virtual ISet<OrderProductIngredientEntity> Ingredients { get; set; } =
        new HashSet<OrderProductIngredientEntity>();
}