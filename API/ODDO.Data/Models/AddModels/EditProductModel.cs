using System.Security.Cryptography;

namespace ODDO.Data.Models.AddModels;

public class EditProductModel: BaseModel
{
    public string Name { get; set; }
    public double Price { get; set; }
    public List<int>? IngredientsToAdd { get; set; }
    public List<int>? IngredientsToRemove { get; set; }
}