using ODDO.Data.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using ODDO.Data.Models.AddModels;
using System.Threading.Tasks;
using System.Net.Http;

namespace ODDO.Client.Network
{
    //get, getbyid, add, edit, delete
    public partial class API
    {
        public static async Task<List<TableModel>?> GetTable()
        {
            return await GetRequest<List<TableModel>>("table");
        }


        public static async Task<TableModel?> GetTableById(int id)
        {
            return await GetRequest<TableModel>("table", $"getById?id={id}");
        }

        public static async Task<TableModel?> AddTable(object table)
        {
            return await PostRequest<TableModel>("table", "add", table);
        }

        public static async Task<TableModel?> EditTable(object table)
        {
            return await PostRequest<TableModel>("table", "edit", table);
        }

        public static async Task<HttpResponseMessage> DeleteTable(int id)
        {
            return await DeleteRequest("product", id);
        }
    }
}
