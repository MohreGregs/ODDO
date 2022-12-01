using ODDO.Client.Components;
using ODDO.Client.Network;
using ODDO.Data.Enums;
using ODDO.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ODDO.Client.Views
{
    /// <summary>
    /// Interaction logic for Orders.xaml
    /// </summary>
    public partial class Orders : UserControl
    {
        private List<OrderModel> data;

        private String sortAttribute;

        bool sortDir = false;

        public Orders()
        {
            InitializeComponent();
            getOrders();
        }

        public async void getOrders()
        {
            var loadedOrders = await API.GetOrders();
            if (loadedOrders != null)
            {
                this.data = SortOrders(loadedOrders);
                OrderList.ItemsSource = this.data;
            }
        }

        private List<OrderModel> SortOrders(List<OrderModel> rows)
        {
            if (this.sortAttribute == "Waiter")
            {
                if (this.sortDir)
                {
                    rows = rows.OrderByDescending(o => o.Waiter).ToList();
                }
                else
                {
                    rows = rows.OrderBy(o => o.Waiter).ToList();
                }
            }
            else if (this.sortAttribute == "Status")
            {
                if (this.sortDir)
                {
                    rows = rows.OrderByDescending(o => o.Status).ToList();
                }
                else
                {
                    rows = rows.OrderBy(o => o.Status).ToList();
                }
            }
            else
            {
                if (this.sortDir)
                {
                    rows = rows.OrderByDescending(o => o.Id).ToList();
                }
                else
                {
                    rows = rows.OrderBy(o => o.Id).ToList();
                }
            }
            return rows;
        }

        private void OnSort(object sender, RoutedEventArgs e)
        {
            var x = sender as GridViewColumnHeader;
            if (this.data != null && x != null && x.Name != null)
            {
                if (this.sortAttribute == x.Name)
                {
                    this.sortDir = !this.sortDir;
                }
                else
                {
                    this.sortAttribute = x.Name;
                }
                OrderList.ItemsSource = SortOrders(this.data);
            }
        }

        private void ReloadOrders(object sender, RoutedEventArgs e)
        {
            getOrders();
        }

        private async void SetInProgressStatus(object sender, RoutedEventArgs e)
        {
            int id = (int)((Button)sender).Tag;
            await API.SetStatus(id, Data.Enums.Status.InProgress);
            getOrders();
        }

        private async void SetReadyStatus(object sender, RoutedEventArgs e)
        {
            int id = (int)((Button)sender).Tag;
            await API.SetStatus(id, Data.Enums.Status.Done);
            getOrders();
        }

        private async void OpenOrder(object sender, RoutedEventArgs e)
        {
            int id = (int)((Button)sender).Tag;
            var order = this.data.Find(o => o.Id == 1);
            if (order != null)
            {
                var productList = order.Products;

                List<ProductListEntry> entries = new List<ProductListEntry>();

                foreach (var product in productList )
                {
                    ProductListEntry entry = new ProductListEntry();
                    entry.Name = product.Product.Name;
                    entry.Count = product.Count.ToString();
                    var ingredients = product.Ingredients;
                    string extras = "";
                    if (ingredients != null)
                    {
                        bool first = true;
                        foreach (OrderProductIngredientModel ingredient in ingredients)
                        {
                            if (!first)
                            {
                                extras += ", ";
                            }
                            extras += ingredient.Ingredient.Name;
                        }
                        entry.Extras = extras;
                    }
                    entries.Add(entry);
                }

                ProductList.ItemsSource = entries;
            }
        }

        public class ProductListEntry
        {
            public string Name { get; set; }
            public string Count { get; set; }
            public string Extras { get; set; }
        }
    }
}
