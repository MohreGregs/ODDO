using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database.Configurations; 

public class OrderProductIngredientConfiguration: IEntityTypeConfiguration<OrderProductIngredientEntity> {
    public void Configure(EntityTypeBuilder<OrderProductIngredientEntity> builder) {
        builder.HasKey(x => x.Id);
        builder.HasOne(x => x.OrderProduct).WithMany(x => x.Ingredients);
        builder.HasOne(x => x.Ingredient).WithMany(x => x.Orders);
    }
}