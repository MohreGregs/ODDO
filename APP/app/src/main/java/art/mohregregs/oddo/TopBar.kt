package art.mohregregs.oddo

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun TopBar(
    navController: NavController,
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    TopAppBar (
        title = {
            Text(text = getTitleByRoute(currentDestination?.route))
        },
        navigationIcon = {
            if(currentDestination != null && (currentDestination.route != "home" && currentDestination.route != "homeOrder")){
                IconButton(onClick = {navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Menu")
                }
            }
        }
    )
}

fun getTitleByRoute(route:String?): String {
    return when (route) {
        "home" -> DrawerScreens.Home.title
        "login" -> DrawerScreens.Login.title
        "homeOrder" -> DrawerScreens.HomeOrder.title
        "order" -> DrawerScreens.Order.title
        "checkout" -> DrawerScreens.Checkout.title
        "currentOrders" -> DrawerScreens.Checkout.title
        else -> "ODDO"
    }
}