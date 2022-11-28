using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations; 

public class WaiterConfiguration: IEntityTypeConfiguration<WaiterEntity> {
    public void Configure(EntityTypeBuilder<WaiterEntity> builder) {
        builder.HasKey(x => x.Id);
        builder.HasMany(x => x.Orders).WithOne(x => x.Waiter);
    }
}