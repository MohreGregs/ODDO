namespace ODDO.Data.Models.AddModels; 

public class AddProductModel: BaseModel {
    public string Name { get; set; }
    public double Price { get; set; }
    public List<int> IngredientIds { get; set; } = new();
}