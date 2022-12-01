package art.mohregregs.oddo.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R
import art.mohregregs.oddo.network.models.ProductModel
import art.mohregregs.oddo.views.viewmodel.OrderViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import art.mohregregs.oddo.network.models.IngredientModel
import art.mohregregs.oddo.views.viewmodel.models.IngredientWithCount
import art.mohregregs.oddo.views.viewmodel.models.ProductWithCount

@Composable
fun Order(navController: NavController, orderViewModel: OrderViewModel){

    orderViewModel.getProducts(LocalContext.current)

    val products: List<ProductWithCount> by orderViewModel.products.observeAsState(listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (products.isNotEmpty()) {
            LazyColumn() {
                items(items = products, itemContent ={ product ->
                    ProductListItem(productModel = product, orderViewModel = orderViewModel)
                    Divider(color = Color.LightGray, thickness = 1.dp)
                })
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { navController.navigate(DrawerScreens.Checkout.route) }
            ) {
                Text(text = stringResource(R.string.to_checkout))
            }
        } else {
            Text(text = stringResource(R.string.no_products_available))
        }
    }
}

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
                Row() {
                    Column() {
                        Text(text = productModel.product.name, modifier = Modifier.padding(4.dp), fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = productModel.product.price.toString() + "€")
                    IconButton(onClick = { isExpanded != isExpanded }) {
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
                    }
                }
            }


            AnimatedVisibility(visible = isExpanded, modifier = Modifier.padding(4.dp)) {
                Column(){
                    if(productModel.ingredients.isNotEmpty()){
                        productModel.ingredients.forEach { extra ->
                            ExtraItem(productId = productModel.product.id, extra = extra, orderViewModel = orderViewModel)
                            Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }

                    Text(text = stringResource(R.string.total) + ": ")

                    Button(onClick = { /*TODO: Add to list of products*/ }) {
                        Text(text = stringResource(R.string.add_to_order))
                    }
                }
            }
        }
    }
}

@Composable
fun ExtraItem(productId: Int, extra: IngredientWithCount, orderViewModel: OrderViewModel){
    var checked by remember{ mutableStateOf(false) }
    Row(modifier = Modifier.clickable {
        if(!checked){
            checked = true
        }

        orderViewModel.setAmountOfExtra(productId, extra.ingredient.id, extra.count + 1)
    }) {
        Text(text = extra.ingredient.name)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = extra.ingredient.price.toString() + "€")
        Text(text = extra.count.toString())
        Checkbox(checked = checked, onCheckedChange = {
            if(checked){
                checked = false
                orderViewModel.setAmountOfExtra(productId, extra.ingredient.id, 0)
            }else{
                checked = true
                orderViewModel.setAmountOfExtra(productId, extra.ingredient.id, extra.count +1)
            }
        })
    }
}