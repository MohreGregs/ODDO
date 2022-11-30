package art.mohregregs.oddo.views

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens

@Composable
fun Login(navController: NavController){
    Button(onClick = { navController.navigate(DrawerScreens.Order.route) }) {
        Text(text = "Login")
    }
}