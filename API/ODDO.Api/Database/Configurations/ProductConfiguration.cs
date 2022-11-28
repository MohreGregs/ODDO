using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations;

public class ProductConfiguration: IEntityTypeConfiguration<ProductEntity>
{
    public void Configure(EntityTypeBuilder<ProductEntity> builder)
    {
        builder.HasKey(x => x.Id);
    }
}