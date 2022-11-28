using Microsoft.EntityFrameworkCore;
using ODDOApi.Database.Configurations;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database;

public class DatabaseContext : DbContext
{
    public System.Data.Entity.DbSet<IngredientEntity> Ingredients { get; set; }
    public System.Data.Entity.DbSet<OrderEntity> Orders { get; set; }
    public System.Data.Entity.DbSet<OrderProductEntity> OrderProducts { get; set; }
    public System.Data.Entity.DbSet<OrderProductIngredientEntity> OrderProductIngredients { get; set; }
    public System.Data.Entity.DbSet<ProductEntity> Products { get; set; }
    public System.Data.Entity.DbSet<TableEntity> Tables { get; set; }
    public System.Data.Entity.DbSet<WaiterEntity> Waiter { get; set; }
    
    public DatabaseContext(bool deleteDb = false)
    {
        if(deleteDb)
            Database.EnsureDeleted();
        
        Database.EnsureCreated();
        
    }
    
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlite("Data Source=database.db");
        optionsBuilder.UseLazyLoadingProxies();
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder) {
        modelBuilder.ApplyConfiguration(new IngredientConfiguration());
        modelBuilder.ApplyConfiguration(new OrderConfiguration());
        modelBuilder.ApplyConfiguration(new OrderProductConfiguration());
        modelBuilder.ApplyConfiguration(new OrderProductIngredientConfiguration());
        modelBuilder.ApplyConfiguration(new ProductConfiguration());
        modelBuilder.ApplyConfiguration(new TableConfiguration());
        modelBuilder.ApplyConfiguration(new WaiterConfiguration());
    }
}