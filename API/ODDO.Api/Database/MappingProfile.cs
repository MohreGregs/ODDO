using AutoMapper;
using ODDO.Data.Models;
using ODDO.Data.Models.AddModels;
using ODDOApi.Database.Entities;

namespace ODDOApi.Database; 

public class MappingProfile: Profile {
    public MappingProfile() {
        CreateMap<IngredientEntity, IngredientModel>();
        CreateMap<OrderEntity, OrderModel>();
        CreateMap<OrderProductEntity, OrderProductModel>();
        CreateMap<OrderProductIngredientEntity, OrderProductIngredientModel>();
        CreateMap<ProductEntity, ProductModel>();
        CreateMap<TableEntity, TableModel>();
        CreateMap<WaiterEntity, WaiterModel>();

        CreateMap<AddProductModel, ProductEntity>();
        CreateMap<AddOrderProductModel, OrderProductEntity>();
        CreateMap<AddOrderProductIngredienModel, OrderProductIngredientEntity>();
        CreateMap<AddOrderModel, OrderEntity>();
        
        CreateMap<IngredientModel, IngredientEntity>();
        CreateMap<OrderModel, OrderEntity>();
        CreateMap<OrderProductModel, OrderProductEntity>();
        CreateMap<OrderProductIngredientModel, OrderProductIngredientEntity>();
        CreateMap<ProductModel, ProductEntity>();
        CreateMap<TableModel, TableEntity>();
        CreateMap<WaiterModel, WaiterEntity>();
    }
}