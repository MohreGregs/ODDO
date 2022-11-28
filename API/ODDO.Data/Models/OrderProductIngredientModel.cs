namespace ODDO.Data.Models; 

public class OrderProductIngredientModel {
    public int Count { get; set; }
    public virtual IngredientModel Ingredient { get; set; }
}