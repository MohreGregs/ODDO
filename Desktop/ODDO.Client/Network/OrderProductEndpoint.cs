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
        public static async Task<List<OrderProductModel>?> GetOrderProduct()
        {
            return await GetRequest<List<OrderProductModel>>("orderproduct");
        }


        public static async Task<OrderProductModel?> GetOrderProductById(int id)
        {
            return await GetRequest<OrderProductModel>("orderproduct", $"getById?id={id}");
        }

        public static async Task<OrderProductModel?> AddOrderProduct(object orderproduct)
        {
            return await PostRequest<OrderProductModel>("orderproduct", "add", orderproduct);
        }

        public static async Task<OrderProductModel?> EditOrderProduct(object orderproduct)
        {
            return await PutRequest<OrderProductModel>("orderproduct", "edit", orderproduct);
        }

        public static async Task<HttpResponseMessage> DeleteOrderProduct(int id)
        {
            return await DeleteRequest("orderproduct", id);
        }



    }
}
