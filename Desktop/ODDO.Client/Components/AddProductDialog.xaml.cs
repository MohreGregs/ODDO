using ODDO.Client.Network;
using ODDO.Data.Models;
using ODDO.Data.Models.AddModels;
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
using System.Windows.Shapes;
using Syncfusion.Windows.Shared;
using ODDO.Client.Views;

namespace ODDO.Client.Components
{
    /// <summary>
    /// Interaktionslogik für AddProductDialog.xaml
    /// </summary>
    public partial class AddProductDialog : Window
    {

        Products products;
        public AddProductDialog(Products products)
        {
            this.products = products;
            InitializeComponent();

            this.fillIngredientList();
        }

        public async void fillIngredientList()
        {   
            LBIngredients.ItemsSource = await API.GetIngredient();
        }

        private async void AddProductButton_Click(object sender, RoutedEventArgs e)
        {
            var name = FieldName.Text;
            var price = FieldPrice.Value;

            //if (name.IsNullOrWhiteSpace() && price == 0) return;

            if(name.IsNullOrWhiteSpace() || price == 0)
            {
                FieldName.BorderBrush  = Brushes.Red;
                FieldPrice.BorderBrush = Brushes.Red;
                ErrorMsg.Visibility = Visibility.Visible;
                return;
            }

            ErrorMsg.Visibility = Visibility.Hidden;

            var newProduct = new AddProductModel();
            newProduct.Name  = FieldName.Text;
            newProduct.Price = (double) FieldPrice.Value;


            foreach(var ingredient in LBIngredients.SelectedItems)
            {
                var ing = ingredient as IngredientModel;
                
                if (ing != null && ing.Id != null)
                {
                    newProduct.IngredientIds.Add((int)ing.Id);
                }
            }
            await API.AddProduct(newProduct);
            this.products.getProducts();
            Close();
        }
    }
}
