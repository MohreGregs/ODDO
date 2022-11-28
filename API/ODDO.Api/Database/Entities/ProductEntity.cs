namespace ODDOApi.Database.Entities;

public class ProductEntity: BaseEntity
{
    public string Name { get; set; }
    public double Price { get; set; }

    public virtual ISet<IngredientEntity> Ingredients { get; set; } = new HashSet<IngredientEntity>();
}