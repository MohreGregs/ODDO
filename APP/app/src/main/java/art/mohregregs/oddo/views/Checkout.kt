package art.mohregregs.oddo.views

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R

@Composable
fun Checkout(navController: NavController){
    Button(onClick = { navController.navigate(DrawerScreens.CurrentOrders.route) }) {
        Text(text = stringResource(id = R.string.checkout))
    }
}