namespace ODDO.Data.Models;

public class ProductModel
{
    public int Id { get; set; }
    public string Name { get; set; }
    public double Price { get; set; }
    
    public HashSet<IngredientModel> Ingredients { get; set; }
}