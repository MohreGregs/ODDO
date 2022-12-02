package art.mohregregs.oddo.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R
import art.mohregregs.oddo.format
import art.mohregregs.oddo.network.models.OrderProductIngredientModel
import art.mohregregs.oddo.network.models.OrderProductModel
import art.mohregregs.oddo.views.viewmodel.OrderViewModel
import art.mohregregs.oddo.views.viewmodel.models.ProductWithCount

@Composable
fun Checkout(navController: NavController, orderViewModel: OrderViewModel){
    val context = LocalContext.current
    val products: List<OrderProductModel> by orderViewModel.productsToOrder.observeAsState(listOf())
    if(products.isNotEmpty()){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
                var scrollState = rememberScrollState()
                Column(modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f)) {
                    products.forEach { product ->
                        CheckoutProductItem(product, orderViewModel, navController, products)
                    }
                }
            
            Row(Modifier.padding(10.dp)) {
                Text(text = stringResource(id = R.string.total), fontSize = 20.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = orderViewModel.getTotalOrderAmount().format(2) + "€", fontSize = 20.sp)
            }
            
            Button(
                onClick = {
                    orderViewModel.addOrder(context)
                    navController.navigate(DrawerScreens.CurrentOrders.route)
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = stringResource(id = R.string.checkout))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckoutProductItem(productModel: OrderProductModel, orderViewModel: OrderViewModel, navController: NavController, products: List<OrderProductModel>){
    Column() {
        ListItem(
            text = {
                Text(text = productModel.count.toString() + "x " + productModel.product.name, fontSize = 20.sp)
            },
            secondaryText = { Text(text = productModel.product.price.format(2))},
            trailing = {
                IconButton(onClick = {
                    if(orderViewModel.removeProductFromOrder(productModel)) navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_product))
                }
            }
        )
        if(productModel.ingredients.isNotEmpty()){
            productModel.ingredients.forEach { extra ->
                CheckoutIngredientItem(extra, orderViewModel, productModel)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckoutIngredientItem(extra: OrderProductIngredientModel, orderViewModel: OrderViewModel, product: OrderProductModel){
    Column(Modifier.padding(start = 10.dp)) {
        ListItem(
            text = {
                Text(text = extra.count.toString() + "x " + extra.ingredient.name)
            },
            secondaryText = { Text(text = extra.ingredient.price.format(2) + "€")},
            trailing = {
                IconButton(onClick = {
                    orderViewModel.removeExtraFromProduct(product, extra)
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_product))
                }
            }
        )
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}