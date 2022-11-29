namespace ODDO.Data.Models; 

public class OrderProductIngredientModel: BaseModel {
    public virtual IngredientModel Ingredient { get; set; }
}