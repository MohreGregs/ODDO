using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace ODDOApi.Database.Entities;

public class OrderEntity: BaseEntity
{
    public virtual Table Table { get; set; }
}