package art.mohregregs.oddo.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R
import art.mohregregs.oddo.network.ApiEndpoint
import art.mohregregs.oddo.network.models.ProductModel
import art.mohregregs.oddo.views.viewmodel.OrderViewModel

@Composable
fun Order(navController: NavController, orderViewModel: OrderViewModel){

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (orderViewModel.products.value?.isNotEmpty() == true) {
            LazyColumn() {

            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                onClick = { navController.navigate(DrawerScreens.Checkout.route) }
            ) {
                Text(text = stringResource(R.string.to_checkout))
            }
        } else {
            Text(text = stringResource(R.string.no_products_available))
        }
    }
}