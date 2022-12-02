namespace ODDO.Data.Models; 

public class OrderProductIngredientModel: BaseModel {
    public int Count { get; set; }
    public virtual IngredientModel Ingredient { get; set; }
}