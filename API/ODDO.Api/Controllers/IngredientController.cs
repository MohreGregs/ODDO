using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ODDO.Data.Models;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

public class IngredientController: BaseController<IngredientController, IngredientEntity, IngredientModel, IngredientModel> {
    public IngredientController(ILogger<IngredientController> logger, IMapper mapper, DatabaseContext context) : base(logger, mapper, context) {
    }
    
    [HttpGet]
    public async Task<ActionResult<List<IngredientEntity>?>> Get() {
        var data = await _context.Set<IngredientEntity>().ToListAsync();

        return Ok(_mapper.Map<List<IngredientModel>>(data));
    }
    
    [HttpGet]
    [Route("getById")]
    public async Task<ActionResult<IngredientEntity?>> GetById(int id = 0) {
        if (id == default) return BadRequest();

        return await _context.Set<IngredientEntity>().FirstOrDefaultAsync(x => x.Id == id);
    }

    [HttpPost]
    [Route("add")]
    public virtual async Task<ActionResult> Add([FromBody] IngredientModel? model) {
        if (model == null)
            return BadRequest();

        var obj = _mapper.Map<IngredientEntity>(model);

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
    public virtual async Task<ActionResult<IngredientModel>> Edit([FromBody] IngredientModel? model) {
        if (model?.Id == default) return BadRequest();

        var objToEdit = await _context.Set<IngredientEntity>().FirstOrDefaultAsync(x => x.Id == model.Id);

        if (objToEdit == default) return BadRequest();

        objToEdit = _mapper.Map<IngredientEntity>(model);

        await _context.SaveChangesAsync();

        return Ok(objToEdit);
    }

    [HttpDelete]
    [Route("delete")]
    public virtual async Task<ActionResult> Delete(int id = 0) {
        if (id == default)
            return BadRequest();

        var objToDelete = await _context.Set<IngredientEntity>().FirstOrDefaultAsync(x => x.Id == id);

        if (objToDelete == default) return NotFound();

        _context.Remove(objToDelete);

        await _context.SaveChangesAsync();

        return Ok();
    }
}