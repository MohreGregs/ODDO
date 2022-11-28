using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations; 

public class TableConfiguration: IEntityTypeConfiguration<TableEntity> {
    public void Configure(EntityTypeBuilder<TableEntity> builder) {
        builder.HasKey(x => x.Id);
        builder.HasMany(x => x.Orders).WithOne(x => x.Table);
    }
}