using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations; 

public class OrderConfiguration : IEntityTypeConfiguration<OrderEntity> {
    public void Configure(EntityTypeBuilder<OrderEntity> builder) {
        builder.HasKey(x => x.Id);
        builder.HasOne(x => x.Table).WithMany(x => x.Orders);
        builder.HasOne(x => x.Waiter).WithMany(x => x.Orders);
        builder.HasMany(x => x.Products).WithOne(x => x.Order);
    }
}