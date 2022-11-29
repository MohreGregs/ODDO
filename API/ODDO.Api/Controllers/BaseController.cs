using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ODDO.Data.Models;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

[ApiController]
[Route("[controller]")]
[Produces("application/json")]
public class BaseController<TController, TEntity, TModel, TAddModel>: ControllerBase 
    where TEntity : BaseEntity
    where TModel : BaseModel
    where TAddModel : BaseModel{
    protected DatabaseContext _context;
    private ILogger<TController> _logger;
    protected IMapper _mapper;

    public BaseController(ILogger<TController> logger, IMapper mapper, DatabaseContext context)
    {
        _logger = logger;
        _mapper = mapper;
        _context = context;
    }
}