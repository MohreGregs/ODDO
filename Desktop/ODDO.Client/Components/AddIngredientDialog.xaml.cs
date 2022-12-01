using ODDO.Client.Network;
using ODDO.Client.Views;
using ODDO.Data.Models.AddModels;
using ODDO.Data.Models;
using Syncfusion.Windows.Shared;
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

namespace ODDO.Client.Components
{
    /// <summary>
    /// Interaction logic for AddIngredientDialog.xaml
    /// </summary>
    public partial class AddIngredientDialog : Window
    {
        Ingredients ingredients;
        public AddIngredientDialog(Ingredients ingredients)
        {
            this.ingredients = ingredients;
            InitializeComponent();
        }

        private async void AddIngredientButton_Click(object sender, RoutedEventArgs e)
        {
            var name = FieldName.Text;
            var price = FieldPrice.Value;

            if (name.IsNullOrWhiteSpace() || price == 0)
            {
                FieldName.BorderBrush = Brushes.Red;
                FieldPrice.BorderBrush = Brushes.Red;
                ErrorMsg.Visibility = Visibility.Visible;
                return;
            }

            ErrorMsg.Visibility = Visibility.Hidden;

            var newIngredient = new IngredientModel();
            newIngredient.Name = FieldName.Text;
            newIngredient.Price = (double)FieldPrice.Value;

            await API.AddIngredient(newIngredient);
            this.ingredients.getIngredients();
            Close();
        }
    }
}
