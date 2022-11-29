using ODDO.Data.Enums;

namespace ODDO.Data.Models;

public class OrderStatusModel: BaseModel
{
    public Status Status { get; set; }
}