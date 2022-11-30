package art.mohregregs.oddo.Views

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens

@Composable
fun Checkout(navController: NavController){
    Button(onClick = { navController.navigate(DrawerScreens.CurrentOrders.route) }) {
        Text(text = "Checkout")
    }
}