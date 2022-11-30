package art.mohregregs.oddo.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R

@Composable
fun CurrentOrders(navController: NavController){
    Column() {
        Button(onClick = { navController.navigate(DrawerScreens.Order.route) }) {
            Text(text = stringResource(R.string.do_another_orer))
        }

        Button(onClick = { navController.navigate(DrawerScreens.HomeOrder.route) }) {
            Text(text = stringResource(R.string.done_with_ordering))
        }
    }
}