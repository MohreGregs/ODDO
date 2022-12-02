using ODDO.Client.Network;
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
using ODDO.Data.Models;
using ODDO.Client.Components;

namespace ODDO.Client.Views
{
    /// <summary>
    /// Interaction logic for Ingredients.xaml
    /// </summary>
    public partial class Ingredients : UserControl
    {

        private List<IngredientModel> data;

        private String sortAttribute;

        bool sortDir = false;

        public Ingredients()
        {
            InitializeComponent();
            getIngredients();
        }

        public async void getIngredients()
        {
            var loadedIncredients = await API.GetIngredient();
            if (loadedIncredients != null)
            {
                this.data = SortIngredients(loadedIncredients);
                IngredientsList.ItemsSource = this.data;
            }
        }

        private List<IngredientModel> SortIngredients(List<IngredientModel> rows)
        {
            if (this.sortAttribute == "Name")
            {
                if (this.sortDir)
                {
                    rows = rows.OrderByDescending(o => o.Name).ToList();
                } else
                {
                    rows = rows.OrderBy(o => o.Name).ToList();
                }
            } else if (this.sortAttribute == "Price")
            {
                if (this.sortDir)
                {
                    rows = rows.OrderByDescending(o => o.Price).ToList();
                }
                else
                {
                    rows = rows.OrderBy(o => o.Price).ToList();
                }
            } else
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
                } else
                {
                    this.sortAttribute = x.Name;
                }
                IngredientsList.ItemsSource = SortIngredients(this.data);
            }
        }

        private void ReloadIngredients(object sender, RoutedEventArgs e)
        {
            getIngredients();
        }

        private void AddIngredients(object sender, RoutedEventArgs e)
        {
            AddIngredientDialog dialog = new AddIngredientDialog(this);
            dialog.Show();
        }

        private void UpdateIngredient(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Update");
        }

        private async void DeleteIngredient(object sender, RoutedEventArgs e)
        {
            int id = (int)((Button)sender).Tag;
            await API.DeleteIngredient(id);
            getIngredients();
        }
    }
}
