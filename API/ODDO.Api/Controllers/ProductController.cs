using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.FileProviders;
using ODDO.Data.Models;
using ODDO.Data.Models.AddModels;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

public class ProductController : BaseController<ProductController, ProductEntity, ProductModel, AddProductModel> {
    public ProductController(ILogger<ProductController> logger, IMapper mapper, DatabaseContext context) : base(logger, mapper, context) {
    }
    
    [HttpGet]
    public async Task<ActionResult<List<ProductEntity>?>> Get() {
        var data = await _context.Set<ProductEntity>().ToListAsync();

        return Ok(_mapper.Map<List<ProductModel>>(data));
    }
    
    [HttpGet]
    [Route("getById")]
    public async Task<ActionResult<ProductEntity?>> GetById(int id = 0) {
        if (id <= 0) return BadRequest();

        var product = await _context.Set<ProductEntity>().FirstOrDefaultAsync(x => x.Id == id);

        return Ok(_mapper.Map<ProductModel>(product));
    }

    [HttpPost]
    [Route("add")]
    public virtual async Task<ActionResult> Add([FromBody] AddProductModel? model) {
        if (model == null)
            return BadRequest();

        var newProduct = new ProductEntity
        {
            Name = model.Name,
            Price = model.Price
        };

        foreach (var ingredientId in model.IngredientIds)
        {
            var ingredient = await _context.Ingredients.FirstOrDefaultAsync(x => x.Id == ingredientId);
            
            if(ingredient == default) continue;
            
            newProduct.Ingredients.Add(ingredient);
        }

        await _context.AddAsync(newProduct);

        try {
            await _context.SaveChangesAsync();
        }
        catch (Exception ex) {
            Console.WriteLine(ex);

            return StatusCode(500, ex.ToString());
        }

        return Ok(_mapper.Map<ProductModel>(newProduct));
    }
    
    [HttpPut]
    [Route("edit")]
    public virtual async Task<ActionResult<ProductModel>> Edit([FromBody] EditProductModel? model) {
        if (model?.Id == default) return BadRequest();

        var product = await _context.Products.FirstOrDefaultAsync(x => x.Id == model.Id);
        if (product == default) return NotFound();

        product.Name = model.Name;
        product.Price = model.Price;

        foreach (var ingredientId in model.IngredientsToAdd)
        {
            var ingredient = await _context.Ingredients.FirstOrDefaultAsync(x => x.Id == ingredientId);
            if (ingredient == default) continue;

            product.Ingredients.Add(ingredient);
        }

        foreach (var ingredientId in model.IngredientsToRemove)
        {
            var ingredient = await _context.Ingredients.FirstOrDefaultAsync(x => x.Id == ingredientId);
            if (ingredient == default) continue;

            product.Ingredients.Remove(ingredient);
        }
        
        await _context.SaveChangesAsync();

        return Ok(_mapper.Map<ProductEntity>(product));
    }

    [HttpDelete]
    [Route("delete")]
    public virtual async Task<ActionResult> Delete(int id = 0) {
        if (id <= 0)
            return BadRequest();

        var objToDelete = await _context.Set<ProductEntity>().FirstOrDefaultAsync(x => x.Id == id);

        if (objToDelete == default) return NotFound();

        _context.Remove(objToDelete);

        await _context.SaveChangesAsync();

        return Ok();
    }
}