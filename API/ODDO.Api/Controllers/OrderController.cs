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

    [HttpPost]
    [Route("add")]
    public override async Task<ActionResult> Add([FromBody] AddOrderModel? model) {
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

        return Problem();
    }
}