using System.Data.Entity;
using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using ODDO.Data.Enums;
using ODDO.Data.Models;
using ODDO.Data.Models.AddModels;
using ODDOApi.Database;
using ODDOApi.Database.Entities;

namespace ODDO.Api.Controllers; 

public class OrderController: BaseController<OrderController, OrderEntity, OrderModel, AddOrderModel> {
    public OrderController(ILogger<OrderController> logger, IMapper mapper, DatabaseContext context) : base(logger, mapper, context) {
    }
    
    [HttpGet]
    public async Task<ActionResult<List<OrderModel>?>> Get() {
        var data = await _context.Set<OrderEntity>().ToListAsync();

        return Ok(_mapper.Map<List<OrderModel>>(data));
    }

    [HttpGet]
    [Route("current")]
    public async Task<ActionResult<List<OrderModel>?>> Current()
    {
        var orders = await _context.Orders.Where(x => x.Status != Status.Done).ToListAsync();

        return Ok(_mapper.Map<List<OrderModel>>(orders));
    }
    
    [HttpGet]
    [Route("getById")]
    public async Task<ActionResult<OrderModel?>> GetById(int id = 0) {
        if (id <= 0) return BadRequest();

        return await _context.Set<OrderModel>().FirstOrDefaultAsync(x => x.Id == id);
    }

    [HttpGet]
    [Route("setStatus")]
    public async Task<ActionResult<OrderModel>> SetStatus(Status status, int id = 0)
    {
        if (id <= 0) return BadRequest();

        var order = await _context.Orders.FirstOrDefaultAsync(x => x.Id == id);
        order.Status = status;

        await _context.SaveChangesAsync();

        return Ok(order);
    }

    [HttpPost]
    [Route("add")]
    public async Task<ActionResult> Add([FromBody] AddOrderModel? model) {
        if (model == null) return BadRequest();

        var waiter = await _context.Waiter.FirstOrDefaultAsync(x => x.Id == model.WaiterId);
        if (waiter == default) return BadRequest();
        
        var table = await _context.Tables.FirstOrDefaultAsync(x => x.Id == model.TableId);
        if (table == default) return BadRequest();

        using (var transaction = await _context.Database.BeginTransactionAsync()) {
            try {
                var newOrder = new OrderEntity {
                    Status = Status.Ordered,
                    Waiter = waiter,
                    Table = table
                };

                foreach (var orderProduct in model.Products) {
                    var product = await _context.Products.FirstOrDefaultAsync(x => x.Id == orderProduct.ProductId);
                    var newProduct = new OrderProductEntity {
                        Count = orderProduct.Count,
                        Product = product
                    };
            
                    foreach (var productIngredient in orderProduct.Ingredients) {
                        var ingredient =
                            await _context.Ingredients.FirstOrDefaultAsync(x => x.Id == productIngredient.IngredientId);

                        if (ingredient == default) continue;
                
                        var newIngredient = new OrderProductIngredientEntity {
                            Count = productIngredient.Count,
                            Ingredient = ingredient
                        };

                        _context.OrderProductIngredients.Add(newIngredient);
                    
                        await _context.SaveChangesAsync();

                        await transaction.CommitAsync();
                        
                        newProduct.Ingredients.Add(newIngredient);
                    }

                    _context.OrderProducts.Add(newProduct);

                    await _context.SaveChangesAsync();

                    await transaction.CommitAsync();
                    
                    newOrder.Products.Add(newProduct);
                }

                _context.Orders.Add(newOrder);

                await _context.SaveChangesAsync();
                
                await transaction.CommitAsync();

                return Ok(newOrder);
            }
            catch (Exception ex) {
                Console.WriteLine(ex);
                await transaction.RollbackAsync();
            }
        }

        return Problem(statusCode: 500, title: "Error while making order");
    }
    
    [HttpDelete]
    [Route("delete")]
    public virtual async Task<ActionResult> Delete(int id = 0) {
        if (id <= 0)
            return BadRequest();

        var objToDelete = await _context.Set<OrderEntity>().FirstOrDefaultAsync(x => x.Id == id);

        if (objToDelete == default) return NotFound();

        _context.Remove(objToDelete);

        await _context.SaveChangesAsync();

        return Ok();
    }
}