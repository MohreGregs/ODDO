namespace ODDOApi.Database.Entities;

public class IngredientEntity: BaseEntity
{
    public string Name { get; set; }
    public double Price { get; set; }

    public ISet<ProductEntity> Products { get; set; } = new HashSet<ProductEntity>();
    public ISet<OrderProductIngredientEntity> Orders { get; set; } = new HashSet<OrderProductIngredientEntity>();
}