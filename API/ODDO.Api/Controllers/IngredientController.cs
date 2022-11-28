using AutoMapper;
using ODDO.Data.Models;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

public class IngredientController: BaseController<IngredientController, IngredientEntity, IngredientModel, IngredientModel> {
    public IngredientController(ILogger<IngredientController> logger, IMapper mapper, DatabaseContext context) : base(logger, mapper, context) {
    }
}