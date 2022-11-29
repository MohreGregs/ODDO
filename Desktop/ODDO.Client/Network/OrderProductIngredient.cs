using System.Collections.Generic;
using System.Threading.Tasks;
using ODDO.Data.Models.AddModels;
using System.Threading.Tasks;
using System.Net.Http;
using ODDO.Data.Models;

namespace ODDO.Client.Network
{
    public partial class API
    {
        public static async Task<List<OrderProductIngredientModel>?> GetOrderProductIngredient()
        {
            return await GetRequest<List<OrderProductIngredientModel>>("orderproductingredient");
        }

        public static async Task<OrderProductIngredientModel?> GetOrderProductIngredientById(int id)
        {
            return await GetRequest<OrderProductIngredientModel>("orderproductingredient", $"getById?id={id}");
        }

        public static async Task<OrderProductIngredientModel?> AddOrderProductIngredient(object order)
        {
            return await PostRequest<OrderProductIngredientModel>("orderproductingredient", "add", order);
        }

        public static async Task<OrderProductIngredientModel?> EditOrderProductIngredient(object order)
        {
            return await PutRequest<OrderProductIngredientModel>("orderproductingredient", "edit", order);
        }

        public static async Task<HttpResponseMessage> DeleteOrderProductIngredient(int id)
        {
            return await DeleteRequest("orderproductingredient", id);
        }
    }
}
