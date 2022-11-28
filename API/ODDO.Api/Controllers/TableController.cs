using AutoMapper;
using ODDO.Data.Models;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

public class TableController: BaseController<TableController, TableEntity, TableModel, TableModel> {
    public TableController(ILogger<TableController> logger, IMapper mapper, DatabaseContext context) : base(logger, mapper, context) {
    }
}