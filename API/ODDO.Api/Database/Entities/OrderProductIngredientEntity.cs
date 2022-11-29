namespace ODDOApi.Database.Entities;

public class OrderProductIngredientEntity: BaseEntity
{
    public int Count { get; set; }
    public virtual OrderProductEntity OrderProduct { get; set; }
    public virtual IngredientEntity Ingredient { get; set; }
}