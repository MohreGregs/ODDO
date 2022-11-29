using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations; 

public class OrderProductConfiguration: IEntityTypeConfiguration<OrderProductEntity> {
    public void Configure(EntityTypeBuilder<OrderProductEntity> builder) {
        builder.HasKey(x => x.Id);
        builder.HasOne(x => x.Product).WithMany(x => x.Orders);
        builder.HasMany(x => x.Ingredients).WithOne(x => x.OrderProduct);
        builder.HasOne(x => x.Order).WithMany(x => x.Products);
    }
}