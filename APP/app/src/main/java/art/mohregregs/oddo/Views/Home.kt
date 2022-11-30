package art.mohregregs.oddo.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens

@Composable
fun Home(navController: NavController){
    Column() {
        Button(onClick = { navController.navigate(DrawerScreens.Login.route) }) {
            Text(text = "Login")
        }
        Button(onClick = { navController.navigate(DrawerScreens.HomeOrder.route) }) {
            Text(text = "On Table")
        }
    }
}