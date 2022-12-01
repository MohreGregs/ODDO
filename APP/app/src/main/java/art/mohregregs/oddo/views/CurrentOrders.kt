package art.mohregregs.oddo.views

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R
import art.mohregregs.oddo.format
import art.mohregregs.oddo.network.enums.Status
import art.mohregregs.oddo.network.models.OrderModel
import art.mohregregs.oddo.views.viewmodel.OrderViewModel
import art.mohregregs.oddo.views.viewmodel.models.ProductWithCount
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CurrentOrders(navController: NavController, orderViewModel: OrderViewModel){
    val context = LocalContext.current
    val composableScope = rememberCoroutineScope()

    val orders: List<OrderModel> by orderViewModel.currentOrdersOfTable.observeAsState(listOf())

    if(orders.isNotEmpty()){

        composableScope.launch {
            orderViewModel.getOrderStatusesByTableId(context)
        }

        Column(modifier = Modifier.padding(10.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items = orders, itemContent ={ order ->
                    OrderListItem(order = order)
                    Divider(color = Color.DarkGray, thickness = 1.dp)
                })
            }
            Row(modifier = Modifier.fillMaxWidth() ) {
                Button(onClick = {
                    orderViewModel.getProducts(context)
                    navController.navigate(DrawerScreens.Order.route)
                }, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.do_another_orer))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    orderViewModel.resetViewModel()
                    navController.navigate(DrawerScreens.HomeOrder.route)
                }, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(R.string.done_with_ordering))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderListItem(order: OrderModel){
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .clickable {
                isExpanded = !isExpanded
            }
            .fillMaxWidth(),
    ) {
        Column {
            Box{
                ListItem(
                    text = { Text(text = "Order"+ "#" + order.id, fontSize = 20.sp)},
                    trailing = {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "Status", tint = getIconFill(order.status))
                    }
                )
            }

            AnimatedVisibility(visible = isExpanded, modifier = Modifier.padding(4.dp)) {
                Column(){
                    if(order.products.isNotEmpty()){
                        order.products.forEach { product ->
                            ListItem(
                                text = { Text(text = product.product.name)}
                            )
                            if(product.ingredients.isNotEmpty()){
                                product.ingredients.forEach { extra ->
                                    ListItem(
                                        text = { Text(text = extra.ingredient.name)}
                                    )
                                    Divider(color = Color.LightGray, thickness = 1.dp)
                                }
                            }
                            Divider(color = Color.Gray, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}

fun getIconFill(status: Int): Color =
    when(status){
        0 -> Color.Red
        1 -> Color.Yellow
        2 -> Color.Green
        else -> Color.Gray
    }
