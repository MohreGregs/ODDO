using System.Collections.Generic;
using System.Net.Cache;
using System.Net.Http;
using System.Threading.Tasks;
using ODDO.Data.Models;
using ODDO.Data.Models.AddModels;

namespace ODDO.Client.Network
{
    //get, getbyid, add, edit, delete
    public partial class API
    {
        //gets all Product-Records
        public static async Task<List<ProductModel>?> GetProduct() 
        {
            return await GetRequest<List<ProductModel>>("product");
        }
        
        public static async Task<ProductModel?> GetProductById(int id) 
        {
            return await GetRequest<ProductModel>("product", $"getById?id={id}");
        }

        public static async Task<ProductModel?> AddProduct(AddProductModel product) 
        {
            return await PostRequest<ProductModel>("product", "add", product);
        }

        public static async Task<ProductModel?> EditProduct(object product)
        {
            return await PutRequest<ProductModel>("product", "edit", product);
        }

        public static async Task<HttpResponseMessage> DeleteProduct(int id)
        {
            return await DeleteRequest("product", id);
        }



    }
}