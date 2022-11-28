using AutoMapper;
using ODDO.Data.Models;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

public class WaiterController: BaseController<WaiterController, WaiterEntity, WaiterModel, WaiterModel> {
    public WaiterController(ILogger<WaiterController> logger, IMapper mapper, DatabaseContext context) : base(logger, mapper, context) {
    }
}