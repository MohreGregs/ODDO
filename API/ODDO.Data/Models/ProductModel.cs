namespace ODDO.Data.Models;

public class ProductModel: BaseModel
{
    public string Name { get; set; }
    public double Price { get; set; }
    
    public HashSet<IngredientModel> Ingredients { get; set; }
}