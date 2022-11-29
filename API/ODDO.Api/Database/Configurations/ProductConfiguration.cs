using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations;

public class ProductConfiguration: IEntityTypeConfiguration<ProductEntity>
{
    public void Configure(EntityTypeBuilder<ProductEntity> builder)
    {
        builder.HasKey(x => x.Id);
        builder.HasMany(x => x.Orders).WithOne(x => x.Product);
        builder.HasMany(x => x.Ingredients).WithMany(x => x.Products);
    }
}