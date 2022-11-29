namespace ODDO.Data.Models.AddModels; 

public class AddOrderModel: BaseModel {
    public int TableId { get; set; }
    public int WaiterId { get; set; }
    public List<AddOrderProductModel> Products { get; set; }
}