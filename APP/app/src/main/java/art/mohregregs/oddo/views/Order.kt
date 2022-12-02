package art.mohregregs.oddo.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R
import art.mohregregs.oddo.views.viewmodel.OrderViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import art.mohregregs.oddo.format
import art.mohregregs.oddo.network.models.OrderProductModel
import art.mohregregs.oddo.views.viewmodel.models.IngredientWithCount
import art.mohregregs.oddo.views.viewmodel.models.ProductWithCount

@Composable
fun Order(navController: NavController, orderViewModel: OrderViewModel){
    val products: List<ProductWithCount> by orderViewModel.products.observeAsState(listOf())


    OrderScreen(navController, products, orderViewModel)
}

@Composable
fun OrderScreen(navController: NavController, products: List<ProductWithCount>, orderViewModel: OrderViewModel){
    val orderedProducts: List<OrderProductModel> by orderViewModel.productsToOrder.observeAsState(listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (products.isNotEmpty()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items = products, itemContent ={ product ->
                    ProductListItem(productModel = product, orderViewModel = orderViewModel)
                    Divider(color = Color.Gray, thickness = 1.dp)
                })
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = orderedProducts.isNotEmpty(),
                onClick = {
                    navController.navigate(DrawerScreens.Checkout.route)
                }
            ) {
                Text(text = stringResource(R.string.to_checkout))
            }
        } else {
            Text(text = stringResource(R.string.no_products_available))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductListItem(productModel: ProductWithCount, orderViewModel: OrderViewModel){
    var isExpanded by rememberSaveable{mutableStateOf(false)}
    Card(
        modifier = Modifier
            .clickable {
                isExpanded = !isExpanded
            }
            .fillMaxWidth(),
    ){
        Column() {
            Box{
                ListItem(
                    text = {
                        Text(text = productModel.product.name, modifier = Modifier.padding(4.dp), fontSize = 30.sp)
                    },
                    secondaryText = {
                        Text(text = productModel.product.price.format(2) + "€")
                    },
                    trailing = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                orderViewModel.setAmountOfProduct(productId = productModel.product.id, productModel.count + 1)
                                isExpanded = true
                            }) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                            Text(text = productModel.count.toString())
                            IconButton(onClick = {
                                orderViewModel.setAmountOfProduct(productId = productModel.product.id, productModel.count - 1)
                                if (productModel.count == 0){
                                    isExpanded = false
                                }
                            }, enabled = productModel.count != 0) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                            }
                        }
                    }
                )
            }


            AnimatedVisibility(visible = isExpanded, modifier = Modifier.padding(4.dp)) {
                Column(){
                    if(productModel.ingredients.isNotEmpty()){
                        productModel.ingredients.forEach { extra ->
                            ExtraItem(productId = productModel.product.id, extra = extra, orderViewModel = orderViewModel)
                            Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }

                    Row(modifier = Modifier.padding(5.dp)) {
                        Text(text = stringResource(R.string.total) + ":")
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = orderViewModel.getTotalAmountOfProduct(productModel).format(2) + "€")
                    }

                    Button(onClick = {
                        orderViewModel.addProductToOrder(productModel)
                        orderViewModel.resetProductCount(productModel.product.id)
                        isExpanded = false
                    }, enabled = productModel.count != 0) {
                        Text(text = stringResource(R.string.add_to_order))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExtraItem(productId: Int, extra: IngredientWithCount, orderViewModel: OrderViewModel){
    ListItem(
    text = {
        Text(text = extra.ingredient.name)
    },
    secondaryText = {
        Text(text = extra.ingredient.price.format(2) + "€")
    },
    trailing = {
        Row(verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = {
                orderViewModel.setAmountOfExtra(productId, extra.ingredient.id, extra.count + 1)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
            Text(text = extra.count.toString())
            IconButton(onClick = {
                orderViewModel.setAmountOfExtra(productId, extra.ingredient.id, extra.count - 1)
            }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
            }
        }
    })
}
