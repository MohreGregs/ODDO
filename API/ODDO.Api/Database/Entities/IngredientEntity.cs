namespace ODDOApi.Database.Entities;

public class IngredientEntity: BaseEntity
{
    public string Name { get; set; }
    public double Price { get; set; }

    public virtual ISet<ProductEntity> Products { get; set; } = new HashSet<ProductEntity>();
    public virtual ISet<OrderProductIngredientEntity> Orders { get; set; } = new HashSet<OrderProductIngredientEntity>();
}