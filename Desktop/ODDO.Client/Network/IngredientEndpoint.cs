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
        public static async Task<List<IngredientModel>?> GetIngredient()
        {
            return await GetRequest<List<IngredientModel>>("ingredient");
        }

        public static async Task<IngredientModel?> GetIngredientById(int id)
        {
            return await GetRequest<IngredientModel>("ingredient", $"getById?id={id}");
        }

        public static async Task<IngredientModel?> AddIngredient(object ingredient)
        {
            return await PostRequest<IngredientModel>("ingredient", "add", ingredient);
        }

        public static async Task<IngredientModel?> EditIngredient(object ingredient)
        {
            return await PostRequest<IngredientModel>("ingredient", "edit", ingredient);
        }

        public static async Task<HttpResponseMessage> DeleteIngredient(int id)
        {
            return await DeleteRequest("ingredient", id);
        }
    }
}
