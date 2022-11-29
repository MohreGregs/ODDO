using ODDO.Data.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ODDO.Data.Models.AddModels;
using System.Threading.Tasks;
using System.Net.Http;
using ODDO.Data.Enums;

namespace ODDO.Client.Network
{
    public partial class API
    {
        public static async Task<List<OrderModel>?> GetOrders()
        {
            return await GetRequest<List<OrderModel>>("order");
        }

        public static async Task<List<OrderModel>?> GetCurrentOrders()
        {
            return await GetRequest<List<OrderModel>>("order", "current");
        }

        public static async Task<OrderModel?> GetOrderById(int id)
        {
            return await GetRequest<OrderModel>("order", $"getById?id={id}");
        }

        public static async Task<List<OrderModel>?> GetStatusByTableId(int tableId)
        {
            return await GetRequest<List<OrderModel>>("order", $"getByTableId?tableid={tableId}");
        }

        public static async Task<Object?> SetStatus(int id, Status status)
        {
            return await GetRequest<List<Object>>("order", $"setStatus?tableid={id}&status={status}");
        }

        public static async Task<OrderModel?> AddOrder(object order)
        {
            return await PostRequest<OrderModel>("order", "add", order);
        }

        public static async Task<OrderModel?> EditOrder(object order)
        {
            return await PutRequest<OrderModel>("order", "edit", order);
        }

        public static async Task<HttpResponseMessage> DeleteOrder(int id)
        {
            return await DeleteRequest("order", id);
        }
    }
}
