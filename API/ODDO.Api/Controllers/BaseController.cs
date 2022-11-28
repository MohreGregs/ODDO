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
    
    [HttpGet]
    public async Task<ActionResult<List<TEntity>?>> Get() {
        var data = await _context.Set<TEntity>().ToListAsync();

        return Ok(_mapper.Map<List<TModel>>(data));
    }
    
    [HttpGet]
    [Route("getById")]
    public async Task<ActionResult<TEntity?>> GetById(int id = 0) {
        if (id == default) return BadRequest();

        return await _context.Set<TEntity>().FirstOrDefaultAsync(x => x.Id == id);
    }

    [HttpPost]
    [Route("add")]
    public virtual async Task<ActionResult> Add([FromBody] TAddModel? model) {
        if (model == null)
            return BadRequest();

        var obj = _mapper.Map<TEntity>(model);

        if (obj == null)
            return BadRequest();

        await _context.AddAsync(obj);

        try {
            await _context.SaveChangesAsync();
        }
        catch (Exception ex) {
            Console.WriteLine(ex);

            return StatusCode(500, ex.ToString());
        }

        return Ok(obj);
    }

    [HttpPut]
    [Route("edit")]
    public virtual async Task<ActionResult<TModel>> Edit([FromBody] TModel? model) {
        if (model?.Id == default) return BadRequest();

        var objToEdit = await _context.Set<TEntity>().FirstOrDefaultAsync(x => x.Id == model.Id);

        if (objToEdit == default) return BadRequest();

        objToEdit = _mapper.Map<TEntity>(model);

        await _context.SaveChangesAsync();

        return Ok(objToEdit);
    }

    [HttpDelete]
    [Route("delete")]
    public virtual async Task<ActionResult> Delete(int id = 0) {
        if (id == default)
            return BadRequest();

        var objToDelete = await _context.Set<TEntity>().FirstOrDefaultAsync(x => x.Id == id);

        if (objToDelete == default) return NotFound();

        _context.Remove(objToDelete);

        await _context.SaveChangesAsync();

        return Ok();
    }
}