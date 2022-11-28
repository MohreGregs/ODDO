using AutoMapper;
using ODDO.Data.Models;
using ODDO.Data.Models.AddModels;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

public class ProductController : BaseController<ProductController, ProductEntity, ProductModel, AddProductModel> {
    public ProductController(ILogger<ProductController> logger, IMapper mapper, DatabaseContext context) : base(logger, mapper, context) {
    }
}