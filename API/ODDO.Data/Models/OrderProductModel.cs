namespace ODDO.Data.Models; 

public class OrderProductModel: BaseModel {
    public int Count { get; set; }
    public ProductModel Product { get; set; }
    
    public HashSet<OrderProductIngredientModel> Ingredients { get; set; }
}