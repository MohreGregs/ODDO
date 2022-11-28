namespace ODDO.Data.Models.AddModels; 

public class AddOrderProductIngredienModel: BaseModel {
    public int IngredientId { get; set; }
    public int Count { get; set; }
}