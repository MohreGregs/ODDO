using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations; 

public class IngredientConfiguration: IEntityTypeConfiguration<IngredientEntity> {
    public void Configure(EntityTypeBuilder<IngredientEntity> builder) {
        builder.HasKey(x => x.Id);
        builder.HasMany(x => x.Products).WithMany(x => x.Ingredients);
        builder.HasMany(x => x.Orders).WithOne(x => x.Ingredient);
    }
}