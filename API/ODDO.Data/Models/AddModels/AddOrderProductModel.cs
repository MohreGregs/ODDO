namespace ODDO.Data.Models.AddModels; 

public class AddOrderProductModel: BaseModel {
    public int ProductId { get; set; }
    public int Count { get; set; }
    public List<AddOrderProductIngredienModel> Ingredients { get; set; }
}