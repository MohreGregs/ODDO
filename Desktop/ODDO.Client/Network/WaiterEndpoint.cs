using ODDO.Data.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ODDO.Data.Models.AddModels;
using System.Threading.Tasks;
using System.Net.Http;

namespace ODDO.Client.Network
{
    public partial class API 
    {
        public static async Task<List<WaiterModel>?> GetWaiter()
        {
            return await GetRequest<List<WaiterModel>>("waiter");
        }


        public static async Task<WaiterModel?> GetWaiterById(int id)
        {
            return await GetRequest<WaiterModel>("waiter", $"getById?id={id}");
        }

        public static async Task<WaiterModel?> AddWaiter(object waiter)
        {
            return await PostRequest<WaiterModel>("waiter", "add", waiter);
        }

        public static async Task<WaiterModel?> EditWaiter(object waiter)
        {
            return await PostRequest<WaiterModel>("waiter", "edit", waiter);
        }

        public static async Task<HttpResponseMessage> DeleteWaiter(int id)
        {
            return await DeleteRequest("product", id);
        }



    }
}
