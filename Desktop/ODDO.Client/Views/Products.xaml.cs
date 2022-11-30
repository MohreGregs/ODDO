using ODDO.Client.Network;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ODDO.Client.Views
{
    /// <summary>
    /// Interaction logic for Products.xaml
    /// </summary>
    public partial class Products : UserControl
    {

        private List<ProductModel> data;

        private String sortAttribute;

        bool sortDir = false;

        public Products()
        {
            InitializeComponent();
            getProducts();
        }

        private async void getProducts()
        {
            var loadedProducts = await API.GetProduct();
            if (loadedProducts != null)
            {
                this.data = SortProducts(loadedProducts);
                ProductsList.ItemsSource = this.data;
            }
        }

        private List<ProductModel> SortProducts(List<ProductModel> rows)
        {
            if (this.sortAttribute == "Name")
            {
                if (this.sortDir)
                {
                    rows = rows.OrderByDescending(o => o.Name).ToList();
                }
                else
                {
                    rows = rows.OrderBy(o => o.Name).ToList();
                }
            }
            else if (this.sortAttribute == "Price")
            {
                if (this.sortDir)
                {
                    rows = rows.OrderByDescending(o => o.Price).ToList();
                }
                else
                {
                    rows = rows.OrderBy(o => o.Price).ToList();
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
                ProductsList.ItemsSource = SortProducts(this.data);
            }
        }

        private void ReloadProducts(object sender, RoutedEventArgs e)
        {
            getProducts();
        }

        private void AddProducts(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Add");
        }

        private void UpdateProduct(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Update");
        }

        private void DeleteProduct(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Delete");
        }
    }
}
